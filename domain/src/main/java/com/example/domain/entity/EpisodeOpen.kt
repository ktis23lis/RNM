package com.example.domain.entity

import com.google.gson.annotations.SerializedName

data class EpisodeOpen(
    @SerializedName("results")
    val episodeList: ArrayList<Episode>,
    @SerializedName("info")
    val info: Info
)
