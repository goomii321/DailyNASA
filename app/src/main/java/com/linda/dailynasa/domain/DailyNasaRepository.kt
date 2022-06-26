package com.linda.dailynasa.domain

import androidx.paging.PagingData
import com.linda.dailynasa.common.Resource
import com.linda.dailynasa.data.remote.dto.ApodDto
import com.linda.dailynasa.data.remote.dto.MarsRover
import com.linda.dailynasa.data.remote.dto.Photo
import com.linda.dailynasa.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface DailyNasaRepository {

    suspend fun getApodByDate(date:String): Flow<Resource<ApodDto>>

    suspend fun getMarsRoverData(camera:String,page:Int): Flow<Resource<MarsRover>>
    fun getMarsRoverData2(camera:String): Flow<PagingData<Photo>>

    suspend fun insertFavorite(data:Favorite)
    suspend fun getFavorite(type:String,date: String):Favorite?
    fun getAllFavorite():Flow<List<Favorite>>
    suspend fun removeFavorite(id:Int)

}