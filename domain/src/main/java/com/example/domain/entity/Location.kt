package com.example.domain.entity

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("id")
    val locationId: Int,
    @SerializedName("name")
    val locationName : String,
    @SerializedName("type")
    val locationType : String,
    @SerializedName("dimension")
    val locationDimension : String,
    @SerializedName("url")
    val locationUrl : String,
    @SerializedName("residents")
    val locationResidents : ArrayList<String>?
)