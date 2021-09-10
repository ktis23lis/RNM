package com.example.domain.entity

import com.google.gson.annotations.SerializedName

data class LocationOpen(
    @SerializedName("results")
    val locationList: ArrayList<Location>,
    @SerializedName("info")
    val info: Info
)
