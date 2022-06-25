package com.linda.dailynasa.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var imgUrl: String,
    var name: String?,
    var describe:String?,
    var note:String?,
    val type:String,
    val date:String
)
