package com.linda.dailynasa.ui.home.child

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.linda.dailynasa.R
import com.linda.dailynasa.common.Logger
import com.linda.dailynasa.databinding.FragmentRoverBinding
import com.linda.dailynasa.ui.home.HomeViewModel
import com.linda.dailynasa.ui.home.adapter.RoverAdapter


class RoverFragment : Fragment() {

    private lateinit var binding: FragmentRoverBinding
    private val viewModel by viewModels<HomeViewModel>({requireParentFragment()})

    private val roverList = listOf<String>("Curiosity","Opportunity","Spirit")
    private var spinnerList = listOf<String>()
    private lateinit var roverAdapter: RoverAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoverBinding.inflate(inflater, container, false)

        setListener()
        setObserver()

        viewModel.getMarsRover(roverList[0],1)

        return binding.root
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
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                val camera = roverList[pos]
                viewModel.getMarsRover(camera,1)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        roverAdapter = RoverAdapter(viewModel)
        binding.roverRecyclerView.adapter = roverAdapter

    }

    private fun setObserver() {
        viewModel.roverData.observe(viewLifecycleOwner) {
            it?.let {
                Logger.v("roverData ${it.size}  $it")
                roverAdapter.submitList(it)
                viewModel.showLoading(false)
            }
        }
    }
}