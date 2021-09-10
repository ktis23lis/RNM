package com.example.domain.repository

import com.example.domain.entity.*

interface IRepositoryList {

    fun getPersonageArray(
        page: Int?, callback: (result: RepositoryListResult<ArrayList<Personage>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    )

    fun getPersonageFilter(
        page: Int?,
        filter: SearchPersonage?,
        callback: (result: SuccessList<ArrayList<Personage>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    )

    fun getLocationArray(
        page: Int, callback: (result: RepositoryListResult<ArrayList<Location>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    )

    fun getLocationFilter(
        page: Int?,
        filter: SearchLocation?,
        callback: (result: SuccessList<ArrayList<Location>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    )


    fun getEpisodeArray(
        page: Int, callback: (result: RepositoryListResult<ArrayList<Episode>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    )

    fun getEpisodeFilter(
        page: Int?,
        filter: SearchEpisode?,
        callback: (result: SuccessList<ArrayList<Episode>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    )
}