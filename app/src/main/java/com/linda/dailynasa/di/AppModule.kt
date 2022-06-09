package com.linda.dailynasa.di

import android.app.Application
import androidx.room.Room
import com.linda.dailynasa.common.Constants
import com.linda.dailynasa.data.DailyNasaRepositoryImpl
import com.linda.dailynasa.data.local.NasaDatabase
import com.linda.dailynasa.data.remote.NasaApi
import com.linda.dailynasa.domain.DailyNasaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideEkiApi(): NasaApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_TEST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NasaDatabase {
        return Room.databaseBuilder(
            app,
            NasaDatabase::class.java,
            NasaDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDailyNasaRepository(
        api: NasaApi,
        database: NasaDatabase,
    ): DailyNasaRepository {
        return DailyNasaRepositoryImpl(api, database.dao)
    }
}