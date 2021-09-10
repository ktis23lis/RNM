package com.example.domain.convector

interface BaseConvector<in A, out B> {
    fun conv(type : A):B
}