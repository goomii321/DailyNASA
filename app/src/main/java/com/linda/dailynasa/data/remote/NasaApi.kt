package com.linda.dailynasa.data.remote

import com.linda.dailynasa.data.remote.dto.ApodDto
import com.linda.dailynasa.data.remote.dto.MarsRover
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    suspend fun getApodByDate(
        @Query("api_key") key:String,
        @Query("date") date:String
    ): Response<ApodDto>

    @GET("mars-photos/api/v1/rovers/{rover}/photos")
    suspend fun getMarsRoverData(
        @Path("rover") rover:String,
        @Query("api_key") key:String,
        @Query("sol") sol:Int,
        @Query("page") page:Int
    ): Response<MarsRover>
}