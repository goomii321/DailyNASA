package com.linda.dailynasa.ui.home.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.linda.dailynasa.R
import com.linda.dailynasa.data.remote.dto.ApodDto
import com.linda.dailynasa.databinding.FragmentApodBinding
import com.linda.dailynasa.ui.dialog.MessageDialog
import com.linda.dailynasa.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApodFragment : Fragment() {

    private lateinit var binding:FragmentApodBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApodBinding.inflate(inflater,container,false)
        setListener()
        setObserver()
        return binding.root
    }

    private fun setListener() {

    }

    private fun setObserver() {
        viewModel.apodData.observe(viewLifecycleOwner) {
            it?.let {
                setApod(it)
            }
        }
        viewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isBlank()) {
                    setErrorDialog("unknown error")
                    return@observe
                }
                setErrorDialog(it)
            }
        }
    }

    private fun setApod(data:ApodDto) {
        binding.apodImg.let {
            val uri = data.url.toUri().buildUpon().build()
            Glide.with(it.context)
                .load(uri)
                .into(it)
        }
        binding.explanationText.text = data.explanation
    }

    private fun setErrorDialog(msg:String) {
        val rightListener = View.OnClickListener {
            MessageDialog.dialog?.dismiss()
        }
        MessageDialog(requireContext()).apply {
            message = msg
            leftButtonState = null
            rightButtonState = getString(R.string.confirm)
        }.setCustomDialog(null,rightListener)
    }
}