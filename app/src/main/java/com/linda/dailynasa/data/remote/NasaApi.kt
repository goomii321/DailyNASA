package com.linda.dailynasa.data.remote

import com.linda.dailynasa.data.remote.dto.ApodDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    suspend fun getApodByDate(
        @Query("api_key") key:String,
        @Query("date") date:String
    ): Response<ApodDto>

}