package com.example.domain.entity

import com.google.gson.annotations.SerializedName

data class Episode (
    @SerializedName("id")
    val episodeId: Int,
    @SerializedName("name")
    val episodeName : String,
    @SerializedName("episode")
    val episode : String,
    @SerializedName("air_date")
    val episodeAirFate : String,
    @SerializedName("characters")
    val episodeCharacters : ArrayList<String>?
)
