package com.linda.dailynasa.domain

import com.linda.dailynasa.common.Resource
import com.linda.dailynasa.data.remote.dto.ApodDto
import com.linda.dailynasa.data.remote.dto.MarsRover
import kotlinx.coroutines.flow.Flow

interface DailyNasaRepository {

    suspend fun getApodByDate(date:String): Flow<Resource<ApodDto>>

    suspend fun getMarsRoverData(camera:String,page:Int): Flow<Resource<MarsRover>>
}