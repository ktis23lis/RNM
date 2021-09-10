package com.example.domain.entity

import com.google.gson.annotations.SerializedName

data class PersonageOpen(
    @SerializedName("results")
    val personageList: ArrayList<Personage>,
    @SerializedName("info")
    val info: Info
)


