package com.example.rnm.feature.location.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.entity.Episode
import com.example.domain.entity.Location
import com.example.domain.entity.Personage
import com.example.domain.interactors.IDetailInteractor
import com.example.domain.interactors.IListInteractor
import com.example.rnm.feature.base.BaseViewModel
import javax.inject.Inject

class LocationDetailViewModel @Inject constructor(
    private val locationInteractor: IDetailInteractor
): BaseViewModel() {

    private var myId = 0
    private var resultArray = ArrayList<Personage>()

    private val locationMutableLiveData: MutableLiveData<Location> =
        MutableLiveData()
    val locationLiveData : LiveData<Location>
        get() = locationMutableLiveData

    private val personageMutableLiveData: MutableLiveData<ArrayList<Personage>> =
        MutableLiveData()
    val personageLiveData : LiveData<ArrayList<Personage>>
        get() = personageMutableLiveData

    private val loadingMutableLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val loadingLiveData : LiveData<Boolean>
        get() = loadingMutableLiveData

    fun getLocation(id : Int){
        loadingMutableLiveData.value = true
        locationInteractor.getLocationDetails(id){ location ->
            loadingMutableLiveData.value = false
            val result: Location = location.value
            locationMutableLiveData.value = result
            for (i in result.locationResidents.orEmpty()) {
                myId = (i.substring(i.lastIndexOf("/") + 1)).toInt()
                locationInteractor.getPersonageDetails(myId) {
                    resultArray.add(it.value)
                    personageMutableLiveData.value = resultArray
                }
            }
        }
    }
}