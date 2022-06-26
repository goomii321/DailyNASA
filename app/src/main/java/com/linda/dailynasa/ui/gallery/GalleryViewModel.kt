package com.linda.dailynasa.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linda.dailynasa.domain.DailyNasaRepository
import com.linda.dailynasa.domain.model.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: DailyNasaRepository
) : ViewModel() {

    private val _favoriteData = MutableLiveData<List<Favorite>>()
    val favoriteData: LiveData<List<Favorite>> = _favoriteData

    init {
        getAllFavorite()
    }

    private fun getAllFavorite() {
        repository.getAllFavorite().onEach { result ->
            _favoriteData.value = result
        }.launchIn(viewModelScope)
    }
}