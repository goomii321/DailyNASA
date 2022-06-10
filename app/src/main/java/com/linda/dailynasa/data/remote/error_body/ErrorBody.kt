package com.linda.dailynasa.data.remote.error_body

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    val code: Int,
    val msg: String,
    @SerializedName("service_version") val serviceVersion: String
)
