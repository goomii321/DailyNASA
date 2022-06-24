package com.linda.dailynasa.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.GsonBuilder
import com.linda.dailynasa.BuildConfig
import com.linda.dailynasa.common.Resource
import com.linda.dailynasa.data.local.NasaDao
import com.linda.dailynasa.data.remote.NasaApi
import com.linda.dailynasa.data.remote.dto.ApodDto
import com.linda.dailynasa.data.remote.dto.MarsRover
import com.linda.dailynasa.data.remote.dto.Photo
import com.linda.dailynasa.data.remote.error_body.ErrorBody
import com.linda.dailynasa.domain.DailyNasaRepository
import com.linda.dailynasa.ui.home.paging.RoverPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class DailyNasaRepositoryImpl @Inject constructor(
    private val api: NasaApi,
    private val localDatabase: NasaDao
) : DailyNasaRepository {
    private val key = BuildConfig.API_KEY

    override suspend fun getApodByDate(date: String): Flow<Resource<ApodDto>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = api.getApodByDate(key, date)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        emit(Resource.Success(response.body()!!))
                    } else {
                        emit(Resource.Error("no data"))
                    }
                } else {
                    val gson = GsonBuilder().create()

                    response.errorBody()?.let {
                        val mError = gson.fromJson(
                            it.string(), ErrorBody::class.java
                        )
                        emit(Resource.Error(mError.msg.toString() ?: "null"))
                    }
                }
            } catch (e:HttpException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e:IOException) {
                emit(Resource.Error(e.message ?: "Internet error"))
            }
        }
    }

    override suspend fun getMarsRoverData(camera:String,page: Int): Flow<Resource<MarsRover>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = api.getMarsRoverData(camera,key, 1000,page)

                if (response.body() != null) {
                    emit(Resource.Success(response.body()!!))
                } else {
                    emit(Resource.Error("no data"))
                }
            } catch (e:HttpException) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override fun getMarsRoverData2(camera: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(30,2,false),
            pagingSourceFactory = {RoverPagingSource(api, camera)}
        ).flow
    }
}