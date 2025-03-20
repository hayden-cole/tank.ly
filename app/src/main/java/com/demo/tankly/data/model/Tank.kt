package com.demo.tankly.data.model

import com.squareup.moshi.Json

data class Tank(
    val id: Int,
    val name: String,
    val type: String,
    val country: String,
    @Json(name = "year_mfg")
    val yearManufactured: Int,
)