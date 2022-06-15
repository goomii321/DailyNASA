package com.linda.dailynasa.ui.home.child

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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
        binding.loadView.visibility = View.VISIBLE
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
        binding.loadView.visibility = View.VISIBLE
        binding.apodImg.let {
            val uri = data.url.toUri().buildUpon().build()
            Glide.with(it.context)
                .load(uri)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loadView.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loadView.visibility = View.GONE
                        return false
                    }
                })
                .apply (
                    RequestOptions()
                        .placeholder(R.drawable.refresh_48px)
                        .error(R.drawable.broken_image_48px)
                )
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