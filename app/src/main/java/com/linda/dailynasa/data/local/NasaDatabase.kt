package com.linda.dailynasa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.linda.dailynasa.domain.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class NasaDatabase: RoomDatabase() {
    abstract val dao:NasaDao

    companion object {
        const val DATABASE_NAME = "dailyNasaDb"
    }
}