package com.linda.dailynasa.ui.home.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.linda.dailynasa.data.remote.dto.Photo
import com.linda.dailynasa.databinding.FragmentRoverDetailBinding

class RoverDetailFragment : Fragment() {

    private lateinit var binding:FragmentRoverDetailBinding

    private val roverArgs by navArgs<RoverDetailFragmentArgs>()
    private lateinit var roverData:Photo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoverDetailBinding.inflate(inflater,container,false)
        roverData = roverArgs.roverArgs

        setListener()
        setUI(roverData)

        return binding.root
    }

    private fun setListener() {
        binding.leftButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setUI(data:Photo) {
        data.img_src.let {
            val url = it.toUri().buildUpon().build()

            Glide.with(binding.detailImage)
                .load(url).into(binding.detailImage)
        }

        binding.roverNameText.text = data.rover.name
        binding.dateText.text = data.earth_date
        binding.launchDateText.text = data.rover.launch_date
        binding.landingDateText.text = data.rover.landing_date
        binding.cameraText.text = data.camera.full_name
    }
}