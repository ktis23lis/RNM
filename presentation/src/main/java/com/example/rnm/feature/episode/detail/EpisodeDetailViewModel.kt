package com.example.rnm.feature.episode.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.entity.Episode
import com.example.domain.entity.Location
import com.example.domain.entity.Personage
import com.example.domain.interactors.IDetailInteractor
import com.example.domain.interactors.IListInteractor
import com.example.rnm.feature.base.BaseViewModel
import javax.inject.Inject

class EpisodeDetailViewModel @Inject constructor(
    private val episodeInteractor: IDetailInteractor
): BaseViewModel(){

    private var myId = 0
    private var resultArray = ArrayList<Personage>()

    private val episodeMutableLiveData: MutableLiveData<Episode> =
        MutableLiveData()
    val episodeLiveData : LiveData<Episode>
        get() = episodeMutableLiveData

    private val personageMutableLiveData: MutableLiveData<ArrayList<Personage>> =
        MutableLiveData()
    val personageLiveData : LiveData<ArrayList<Personage>>
        get() = personageMutableLiveData

    private val loadingMutableLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val loadingLiveData : LiveData<Boolean>
        get() = loadingMutableLiveData

    var flag = 0

    fun getEpisode(id : Int){
        loadingMutableLiveData.value = true
        episodeInteractor.getEpisodeDetails(id){
                episode ->
            loadingMutableLiveData.value = false
            flag = 1
            val result: Episode = episode.value
            episodeMutableLiveData.value = result

            for (i in result.episodeCharacters.orEmpty()) {

                myId = (i.substring(i.lastIndexOf("/") + 1)).toInt()
                episodeInteractor.getPersonageDetails(myId) {
                    resultArray.add(it.value)
                    personageMutableLiveData.value = resultArray
                }
            }
        }
    }
}