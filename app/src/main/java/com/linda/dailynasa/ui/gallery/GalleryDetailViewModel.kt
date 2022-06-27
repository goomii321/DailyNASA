package com.linda.dailynasa.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.linda.dailynasa.domain.DailyNasaRepository
import com.linda.dailynasa.domain.model.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryDetailViewModel @Inject constructor(
    private val repository: DailyNasaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _favoriteData = MutableLiveData<Favorite>()
    val favoriteData: LiveData<Favorite> = _favoriteData
    val args = savedStateHandle.get<Favorite>("favorite_args")

    val editChange = MutableLiveData<Boolean>()

    private val _toGallery = MutableLiveData<Boolean>()
    val toGallery: LiveData<Boolean> = _toGallery

    init {
        _favoriteData.value = args
    }

    fun removeFavorite(data:Favorite) {
        coroutineScope.launch {
            data.id?.let {
                repository.removeFavorite(it)
                _toGallery.postValue(true)
            }
        }
    }

    fun updateFavorite(data:Favorite) {
        coroutineScope.launch {
            repository.updateFavorite(data)
            editChange.postValue(false)
        }
    }

    fun onNavigated() {
        _toGallery.value = null
    }
}