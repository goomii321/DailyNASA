package com.linda.dailynasa.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.linda.dailynasa.MobileNavigationDirections
import com.linda.dailynasa.common.Logger
import com.linda.dailynasa.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel by viewModels<GalleryViewModel>()
    private lateinit var galleryAdapter:GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)

        galleryAdapter = GalleryAdapter(GalleryAdapter.OnClickListener {
            findNavController().navigate(MobileNavigationDirections.toGalleryDetailPage(it))
        })
        binding.galleryRecyclerView.adapter = galleryAdapter
        binding.galleryRecyclerView.setHasFixedSize(true)

        viewModel.favoriteData.observe(viewLifecycleOwner) {
            it?.let {
                Logger.d("favoriteData = $it")
                galleryAdapter.submitList(it)
            }
        }

        return binding.root
    }

}