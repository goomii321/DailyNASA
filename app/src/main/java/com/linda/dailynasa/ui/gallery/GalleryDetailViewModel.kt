package com.linda.dailynasa.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.linda.dailynasa.domain.DailyNasaRepository
import com.linda.dailynasa.domain.model.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryDetailViewModel @Inject constructor(
    private val repository: DailyNasaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _favoriteData = MutableLiveData<Favorite>()
    val favoriteData: LiveData<Favorite> = _favoriteData
    val args = savedStateHandle.get<Favorite>("favorite_args")

    val editChange = MutableLiveData<Boolean>()

    init {
        _favoriteData.value = args
    }

}