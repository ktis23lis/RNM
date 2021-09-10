package com.example.domain.entity

import com.google.gson.annotations.SerializedName

data class Personage(
    @SerializedName("id")
    val personageId: Int,
    @SerializedName("image")
    val personageImage: String,
    @SerializedName("name")
    val personageName: String,
    @SerializedName("species")
    val personageSpecies: String,
    @SerializedName("status")
    val personageStatus: String,
    @SerializedName("gender")
    val personageGender: String,
    @SerializedName("origin")
    val personageOriginName : PersonageOrigin,
    @SerializedName("location")
    val personageLocation : PersonageOrigin,
    @SerializedName("episode")
    val personageEpisode : ArrayList<String>?
)

data class PersonageOrigin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)