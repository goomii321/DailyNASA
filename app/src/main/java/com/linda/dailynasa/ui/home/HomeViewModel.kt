package com.linda.dailynasa.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.linda.dailynasa.common.Resource
import com.linda.dailynasa.data.remote.dto.ApodDto
import com.linda.dailynasa.data.remote.dto.Photo
import com.linda.dailynasa.domain.DailyNasaRepository
import com.linda.dailynasa.domain.model.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DailyNasaRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _apodData = MutableLiveData<ApodDto?>()
    val apodData:LiveData<ApodDto?> = _apodData

//    private val _roverData = MutableLiveData<List<Photo>?>()
//    val roverData:LiveData<List<Photo>?> = _roverData

    private val _apodFavorite = MutableLiveData<Favorite>()
    val apodFavorite: LiveData<Favorite> = _apodFavorite

    val apodCheckFavorite = MutableLiveData<Boolean>()

    private val _apodErrorMsg = MutableLiveData<String>()
    val apodErrorMsg:LiveData<String> = _apodErrorMsg

    private val _roverErrorMsg = MutableLiveData<String>()
    val roverErrorMsg:LiveData<String> = _roverErrorMsg

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> = _loading

    init {
        showLoading(true)
    }

    fun showLoading(isShow:Boolean) {
        _loading.value = isShow
    }

    fun getApod(input:String?) {
        val date = input ?: ""
        coroutineScope.launch {
            repository.getApodByDate(date)
                .collect{ result ->
                    when(result) {
                        is Resource.Success -> {
//                            Logger.v("result = ${result.data}")
                            _apodData.postValue(result.data)
                        }
                        is Resource.Error -> {
//                            Logger.e("error = ${result.message}")
                            _apodErrorMsg.postValue(result.message ?: "unknown error")
                        }
                        is Resource.Loading -> {
                            _loading.postValue(true)
                        }
                    }
                }
        }
    }

    fun getMarsRover(camera:String,page:Int) {
        coroutineScope.launch {
            repository.getMarsRoverData(camera,page).collect{ result ->
                when (result) {
                    is Resource.Success -> {
//                        Logger.v("mars result = ${result.data}")
//                        _roverData.postValue(result.data?.photos)
                    }
                    is Resource.Error -> {
//                        Logger.e("mars error = ${result.message}")
                        _roverErrorMsg.postValue(result.message ?: "")
                    }
                    is Resource.Loading -> {
                        _loading.postValue(true)
                    }
                }
            }
        }
    }

    fun getMarsRover2(camera: String): Flow<PagingData<Photo>> {
        return repository.getMarsRoverData2(camera).cachedIn(viewModelScope)
    }

    fun checkFavorite(type:String,date:String) {
        coroutineScope.launch {
            _apodFavorite.postValue(repository.getFavorite(type, date))
        }
    }

    fun insertFavorite(favorite: Favorite) {
        coroutineScope.launch {
            repository.insertFavorite(favorite)
            apodCheckFavorite.postValue(true)
        }
    }

    fun removeFavorite(id:Int) {
        coroutineScope.launch {
            repository.removeFavorite(id)
            apodCheckFavorite.postValue(true)
        }
    }
}