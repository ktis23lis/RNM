package com.example.domain.interactors

import com.example.domain.entity.*
import com.example.domain.repository.ErrorList
import com.example.domain.repository.IRepositoryList
import com.example.domain.repository.RepositoryListResult
import com.example.domain.repository.SuccessList
import javax.inject.Inject

interface IListInteractor {

    fun getPersonageArray(
        page: Int, callback: (result: RepositoryListResult<ArrayList<Personage>>) -> Unit,
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

class ListIInteractor @Inject constructor(private val repositoryList: IRepositoryList) :
    IListInteractor {

    override fun getPersonageArray(
        page: Int, callback: (result: RepositoryListResult<ArrayList<Personage>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    ) {
        repositoryList.getPersonageArray(page, {
            when (it) {
                is SuccessList -> {
                    val result: ArrayList<Personage> = it.value
                    callback(SuccessList(result))
                }
                is ErrorList -> {
                    val result: Throwable = it.value
                    callback(ErrorList(result))
                }
            }
        }, {
            val result = it.value
            callbackPage(SuccessList(result))
        })
    }

    override fun getPersonageFilter(
        page: Int?,
        filter: SearchPersonage?,
        callback: (result: SuccessList<ArrayList<Personage>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    ) {
        repositoryList.getPersonageFilter(page, filter, {
            val result: ArrayList<Personage> = it.value
            callback(SuccessList(result))
        }, {
            val result = it.value
            callbackPage(SuccessList(result))
        })
    }

    override fun getLocationArray(
        page: Int,
        callback: (result: RepositoryListResult<ArrayList<Location>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    ) {
        repositoryList.getLocationArray(page, {
            when (it) {
                is SuccessList -> {
                    val result: ArrayList<Location> = it.value
                    callback(SuccessList(result))
                }
                is ErrorList -> {
                    val result: Throwable = it.value
                    callback(ErrorList(result))
                }
            }
        }, {
            val result = it.value
            callbackPage(SuccessList(result))
        })
    }

    override fun getLocationFilter(
        page: Int?,
        filter: SearchLocation?,
        callback: (result: SuccessList<ArrayList<Location>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    ) {
        repositoryList.getLocationFilter(page, filter, {
            val result: ArrayList<Location> = it.value
            callback(SuccessList(result))
        }, {
            val result = it.value
            callbackPage(SuccessList(result))
        })
    }

    override fun getEpisodeArray(
        page: Int,
        callback: (result: RepositoryListResult<ArrayList<Episode>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    ) {
        repositoryList.getEpisodeArray(page, {
            when (it) {
                is SuccessList -> {
                    val result: ArrayList<Episode> = it.value
                    callback(SuccessList(result))
                }
                is ErrorList -> {
                    val result: Throwable = it.value
                    callback(ErrorList(result))
                }
            }
        }, {
            val result = it.value
            callbackPage(SuccessList(result))
        })
    }

    override fun getEpisodeFilter(
        page: Int?,
        filter: SearchEpisode?,
        callback: (result: SuccessList<ArrayList<Episode>>) -> Unit,
        callbackPage: (result: SuccessList<Int>) -> Unit
    ) {
        repositoryList.getEpisodeFilter(page, filter, {
            val result: ArrayList<Episode> = it.value
            callback(SuccessList(result))
        }, {
            val result = it.value
            callbackPage(SuccessList(result))
        })
    }
}