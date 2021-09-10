package com.example.rnm.feature.episode.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.entity.Episode
import com.example.domain.entity.SearchEpisode
import com.example.domain.interactors.IListInteractor
import com.example.domain.repository.ErrorList
import com.example.domain.repository.SuccessList
import com.example.rnm.feature.base.BaseViewModel
import javax.inject.Inject

class EpisodeListViewModel @Inject constructor(
    private val episodeInteractor: IListInteractor
) : BaseViewModel() {

    private val episodeMutableLiveData: MutableLiveData<ArrayList<Episode>> =
        MutableLiveData()
    val episodeLiveData: LiveData<ArrayList<Episode>>
        get() = episodeMutableLiveData

    private val loadingMutableLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    private val errorMutableLiveData: MutableLiveData<String> =
        MutableLiveData()
    val errorLiveData: LiveData<String>
        get() = errorMutableLiveData



    fun getEpisode(page: Int) {
        episodeInteractor.getEpisodeArray(page, {
            when (it) {
                is SuccessList -> {
                    loadingMutableLiveData.value = true
                    val result: ArrayList<Episode> = it.value
                    episodeMutableLiveData.value = result
                    loadingMutableLiveData.value = false
                }
                is ErrorList -> errorMutableLiveData.value = "Необходим доступ к интенету"
            }
        },{
            EpisodeListFragment.allSizePage = it.value
        })
    }

    fun getEpisode(page: Int, filter: SearchEpisode) {
        episodeInteractor.getEpisodeFilter(page, filter, {
            loadingMutableLiveData.value = true
            val result: ArrayList<Episode> = it.value
            episodeMutableLiveData.value = result
            loadingMutableLiveData.value = false
        },{
            EpisodeListFragment.filterSizePage = it.value
        })
    }

    fun updateList() {
        episodeMutableLiveData.value?.clear()
    }
}