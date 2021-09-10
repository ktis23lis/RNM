package com.example.domain.entity

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("pages")
    val pages: Int
)