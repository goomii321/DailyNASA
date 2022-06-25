package com.linda.dailynasa.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Camera(
    val full_name: String,
    val id: Int,
    val name: String,
    val rover_id: Int
) : Parcelable