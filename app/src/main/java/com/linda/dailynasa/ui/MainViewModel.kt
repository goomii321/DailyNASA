package com.linda.dailynasa.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.linda.dailynasa.common.CurrentFragmentType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    val currentFragmentType = MutableLiveData<CurrentFragmentType>().apply {
        value = CurrentFragmentType.HOME
    }
}