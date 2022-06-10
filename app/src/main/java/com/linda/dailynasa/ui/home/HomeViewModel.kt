package com.linda.dailynasa.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.linda.dailynasa.common.Logger
import com.linda.dailynasa.common.Resource
import com.linda.dailynasa.domain.DailyNasaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: DailyNasaRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg:LiveData<String> = _errorMsg

    fun getApod(input:String?) {
        val date = input ?: ""
        coroutineScope.launch {
            repository.getApodByDate(date)
                .collect{ result ->
                    when(result) {
                        is Resource.Success -> {
                            Logger.v("result = ${result.data}")
                        }
                        is Resource.Error -> {
                            Logger.e("error = ${result.message}")
                            _errorMsg.value = result.message ?: "unknown error"
                        }
                        is Resource.Loading -> {}
                    }
                }
        }
    }
}