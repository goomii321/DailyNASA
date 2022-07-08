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
import com.linda.dailynasa.common.Constants
import com.linda.dailynasa.common.Logger
import com.linda.dailynasa.data.remote.dto.ApodDto
import com.linda.dailynasa.databinding.FragmentApodBinding
import com.linda.dailynasa.domain.model.Favorite
import com.linda.dailynasa.ui.dialog.MessageDialog
import com.linda.dailynasa.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@AndroidEntryPoint
class ApodFragment : Fragment() {

    private lateinit var binding: FragmentApodBinding
    private val viewModel by viewModels<HomeViewModel>({ requireParentFragment() })

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApodBinding.inflate(inflater, container, false)

        viewModel.getApod("")

        setListener()
        setObserver()
        return binding.root
    }

    private fun setListener() {
        binding.addFavoriteButton.setOnClickListener { view ->
            viewModel.apodData.value?.let {
                if (!view.isSelected) {
                    addFavorite(it)
                } else {
                    viewModel.apodFavorite.value?.let { favorite ->
                        viewModel.removeFavorite(favorite.id!!)
                    }
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.apodData.observe(viewLifecycleOwner) {
            it?.let {
                setApodInfo(it)
                viewModel.checkFavorite(Constants.APOD, it.date)
                setApodImage(it.url)
                viewModel.showLoading(false)
            }
        }
        viewModel.apodErrorMsg.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isBlank()) {
                    setErrorDialog("unknown error")
                    return@observe
                }
                setErrorDialog(it)
                viewModel.showLoading(false)
            }
        }
        viewModel.apodFavorite.observe(viewLifecycleOwner) {
            binding.addFavoriteButton.isSelected = it != null
        }
        viewModel.apodCheckFavorite.observe(viewLifecycleOwner) {
            if (it == true) {
                viewModel.checkFavorite(Constants.APOD, viewModel.apodData.value!!.date)
                viewModel.apodCheckFavorite.value = null
            }
        }
    }

    private fun setApodImage(url: String) {
        viewModel.showLoading(true)

        try {
            binding.apodImg.let {
                val uri = url.toUri().buildUpon().build()
                Glide.with(requireContext())
                    .load(uri)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            viewModel.showLoading(false)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            viewModel.showLoading(false)
                            return false
                        }
                    })
                    .apply(
                        RequestOptions()
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .placeholder(R.drawable.refresh_48px)
                            .error(R.drawable.broken_image_48px)
                    )
                    .into(it)
            }
        } catch (e:Exception) {
            Logger.e("eeee ${e.message}")
        }
    }

    private fun setApodInfo(data: ApodDto) {
        binding.apodTitleText.text = data.title
        binding.apodDateText.text = data.date
        binding.explanationText.text = data.explanation
        binding.apodCopyrightText.text = getString(R.string.copyright) + data.copyright
    }

    private fun addFavorite(data: ApodDto) {
        val favorite =
            Favorite(
                null,
                data.url,
                data.title,
                data.explanation,
                null,
                Constants.APOD,
                data.date
            )

        viewModel.insertFavorite(favorite)
    }

    private fun setErrorDialog(msg: String) {
        val rightListener = View.OnClickListener {
            MessageDialog.dialog?.dismiss()
        }
        MessageDialog(requireContext()).apply {
            message = msg
            leftButtonState = null
            rightButtonState = getString(R.string.confirm)
        }.setCustomDialog(null, rightListener)
    }
}