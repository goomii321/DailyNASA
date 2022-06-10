package com.linda.dailynasa.data

import com.google.gson.GsonBuilder
import com.linda.dailynasa.BuildConfig
import com.linda.dailynasa.common.Resource
import com.linda.dailynasa.data.local.NasaDao
import com.linda.dailynasa.data.remote.NasaApi
import com.linda.dailynasa.data.remote.dto.ApodDto
import com.linda.dailynasa.data.remote.error_body.ErrorBody
import com.linda.dailynasa.domain.DailyNasaRepository
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
}