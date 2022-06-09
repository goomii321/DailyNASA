package com.linda.dailynasa.ui.home.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.linda.dailynasa.databinding.FragmentApodBinding

class ApodFragment : Fragment() {

    private lateinit var binding:FragmentApodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApodBinding.inflate(inflater,container,false)
        return binding.root
    }

}