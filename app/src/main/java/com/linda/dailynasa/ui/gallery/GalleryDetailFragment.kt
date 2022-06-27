package com.linda.dailynasa.ui.gallery

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.linda.dailynasa.R
import com.linda.dailynasa.common.Logger
import com.linda.dailynasa.databinding.FragmentGalleryDetailBinding
import com.linda.dailynasa.domain.model.Favorite
import com.linda.dailynasa.ui.dialog.MessageDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GalleryDetailFragment : Fragment() {

    private lateinit var binding: FragmentGalleryDetailBinding
    private val viewModel by viewModels<GalleryDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryDetailBinding.inflate(inflater,container,false)

        setListener()
        setObserver()

        return binding.root
    }

    private fun setListener() {
        binding.leftButton.setOnClickListener {
            val rightListener = View.OnClickListener {
                MessageDialog.dialog?.dismiss()
            }
            val leftListener = View.OnClickListener {
                requireActivity().onBackPressed()
                MessageDialog.dialog?.dismiss()
            }
            if (viewModel.editChange.value == true) {
                MessageDialog(requireContext()).apply {
                    message = getString(R.string.leave_alarm)
                    leftButtonState = getString(R.string.confirm)
                    rightButtonState = getString(R.string.cancel)
                }
                    .setCustomDialog(leftListener,rightListener)
            } else {
                requireActivity().onBackPressed()
            }
        }
        binding.deleteButton.setOnClickListener {

        }

        binding.titleText.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.editChange.value = true
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        binding.describeText.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.editChange.value = true
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.noteText.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.editChange.value = true
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        binding.root.setOnClickListener {
            Logger.v("ccccccc")
        }
    }

    private fun setObserver() {
        viewModel.favoriteData.observe(viewLifecycleOwner) {
            it?.let {
                setUI(it)
            }
        }
        viewModel.editChange.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.saveButton.visibility = View.VISIBLE
                binding.deleteButton.visibility = View.GONE
            } else {
                binding.saveButton.visibility = View.GONE
                binding.deleteButton.visibility = View.VISIBLE
            }
        }
    }

    private fun setUI(data:Favorite) {
        val uri = data.imgUrl.toUri().buildUpon().build()
        binding.detailImage.let {
            Glide.with(it.context)
                .load(uri).apply (
                    RequestOptions()
                        .placeholder(R.drawable.refresh_48px)
                        .error(R.drawable.broken_image_48px)
                ).into(it)
        }
        binding.titleText.setText(data.name)
        binding.dateText.text = data.date
        binding.describeText.setText(data.describe)
        binding.noteText.setText("...")
        viewModel.editChange.value = false
    }
}