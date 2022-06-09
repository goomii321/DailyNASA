package com.linda.dailynasa.ui.home.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.linda.dailynasa.databinding.FragmentRoverBinding


class RoverFragment : Fragment() {

    private lateinit var binding:FragmentRoverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoverBinding.inflate(inflater,container,false)
        return binding.root
    }
}