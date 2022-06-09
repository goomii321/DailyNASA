package com.linda.dailynasa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.linda.dailynasa.R
import com.linda.dailynasa.databinding.FragmentHomeBinding
import com.linda.dailynasa.ui.home.adapter.HomeViewPagerAdapter
import com.linda.dailynasa.ui.home.child.ApodFragment
import com.linda.dailynasa.ui.home.child.RoverFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private var titleList = listOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        titleList = listOf(getString(R.string.apod),getString(R.string.mars_rover))
        setListener()
        setObserver()
        return binding.root
    }

    private fun setListener() {
        val viewPagerAdapter =
            HomeViewPagerAdapter(childFragmentManager,viewLifecycleOwner.lifecycle).apply {
                this.fragments = listOf(ApodFragment(), RoverFragment())
            }
        binding.homeViewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout,binding.homeViewPager) { tab,position ->
            tab.text = titleList[position]
        }.attach()
    }

    private fun setObserver() {

    }
}