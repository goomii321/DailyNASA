package com.linda.dailynasa.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.linda.dailynasa.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface NasaDao {

    @Insert
    fun insertFavorite(data:Favorite)

    @Update
    fun updateFavorite(data: Favorite)

    @Query("SELECT * from Favorite ORDER BY date DESC")
    fun getFavorite(): Flow<List<Favorite>>

    @Query("SELECT * from Favorite WHERE :type = type AND :date = date")
    fun getFavoriteByCondition(type:String,date:String): Favorite?

    @Query("DELETE from Favorite WHERE :id = id")
    fun removeFavorite(id:Int)
}