package com.linda.dailynasa.data

import com.linda.dailynasa.data.local.NasaDao
import com.linda.dailynasa.data.remote.NasaApi
import com.linda.dailynasa.domain.DailyNasaRepository
import javax.inject.Inject

class DailyNasaRepositoryImpl @Inject constructor(
    private val api: NasaApi,
    private val localDatabase: NasaDao
) : DailyNasaRepository {
}