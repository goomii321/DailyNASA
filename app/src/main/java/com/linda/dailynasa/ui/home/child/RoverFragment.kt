package com.linda.dailynasa.ui.home.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.linda.dailynasa.R
import com.linda.dailynasa.common.Logger
import com.linda.dailynasa.databinding.FragmentRoverBinding
import com.linda.dailynasa.ui.home.HomeViewModel
import com.linda.dailynasa.ui.home.adapter.RoverPagingAdapter
import kotlinx.coroutines.launch


class RoverFragment : Fragment() {

    private lateinit var binding: FragmentRoverBinding
    private val viewModel by viewModels<HomeViewModel>({requireParentFragment()})

    private val roverList = listOf<String>("Curiosity","Opportunity","Spirit")
    private var spinnerList = listOf<String>()
//    private lateinit var roverAdapter: RoverAdapter
    private lateinit var roverAdapter: RoverPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoverBinding.inflate(inflater, container, false)

        setListener()
        setObserver()

//        viewModel.getMarsRover(roverList[0],1)
        viewModel.showLoading(true)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData(roverList[0])
    }

    private fun setListener() {
        spinnerList = mutableListOf(
            getString(R.string.Curiosity),
            getString(R.string.Opportunity),
            getString(R.string.Spirit)
        )

        val adapter = ArrayAdapter(requireContext(),
            R.layout.item_rover_spinner,
            spinnerList)
        binding.roverSpinner.adapter = adapter
        binding.roverSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val camera = roverList[pos]
//                viewModel.getMarsRover(camera,1)
                getData(camera)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

//        roverAdapter = RoverAdapter(viewModel)
        roverAdapter = RoverPagingAdapter()
        binding.roverRecyclerView.adapter = roverAdapter

        roverAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    Logger.v("NotLoading NotLoading NotLoading")
                    viewModel.showLoading(false)
                }
                is LoadState.Error -> {
                    Logger.e("error ${(it.refresh as LoadState.Error).error.message}")
                    viewModel.showLoading(false)
                }
                is LoadState.Loading -> {
                    Logger.v("Loading Loading Loading")
                    viewModel.showLoading(true)
                }
            }
        }
    }

    private fun setObserver() {
//        viewModel.roverData.observe(viewLifecycleOwner) {
//            it?.let {
//                Logger.v("roverData ${it.size}  $it")
////                roverAdapter.submitList(it)
//                roverAdapter.submitData(it)
//                viewModel.showLoading(false)
//            }
//        }
    }

    private fun getData(camera:String) {
        lifecycleScope.launch {
            viewModel.getMarsRover2(camera).collect{
                roverAdapter.submitData(it)
            }
        }
    }
}