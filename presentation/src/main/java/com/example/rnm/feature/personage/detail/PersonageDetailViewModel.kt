package com.example.rnm.feature.personage.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.entity.Episode
import com.example.domain.entity.Personage
import com.example.domain.entity.SearchLocation
import com.example.domain.interactors.IDetailInteractor
import com.example.domain.interactors.IListInteractor
import com.example.rnm.feature.base.BaseViewModel
import javax.inject.Inject

class PersonageDetailViewModel @Inject constructor(
    private val personageInteractor: IDetailInteractor
    ) : BaseViewModel(){

    private var myId = 0
    private var resultArray = ArrayList<Episode>()

    private val personageMutableLiveData: MutableLiveData<Personage> =
        MutableLiveData()
    val personageLiveData : LiveData<Personage>
        get() = personageMutableLiveData

    private val episodeMutableLiveData: MutableLiveData<ArrayList<Episode>> =
        MutableLiveData()
    val episodeLiveData : LiveData<ArrayList<Episode>>
        get() = episodeMutableLiveData

    private val loadingMutableLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val loadingLiveData : LiveData<Boolean>
        get() = loadingMutableLiveData

    fun getPersonage(id : Int){
        loadingMutableLiveData.value = true
        personageInteractor.getPersonageDetails(id){ personage ->
            loadingMutableLiveData.value = false
            val result: Personage = personage.value
            personageMutableLiveData.value = result
            for (i in result.personageEpisode.orEmpty()) {
                myId = (i.substring(i.lastIndexOf("/") + 1)).toInt()
                personageInteractor.getEpisodeDetails(myId) {
                    resultArray.add(it.value)
                    episodeMutableLiveData.value = resultArray
                }
            }

        }
    }
}