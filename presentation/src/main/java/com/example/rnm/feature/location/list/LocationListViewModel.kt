package com.example.rnm.feature.location.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.entity.Location
import com.example.domain.entity.SearchLocation
import com.example.domain.interactors.IListInteractor
import com.example.domain.repository.ErrorList
import com.example.domain.repository.SuccessList
import com.example.rnm.feature.base.BaseViewModel
import javax.inject.Inject

class LocationListViewModel @Inject constructor(
    private val locationInteractor: IListInteractor
) : BaseViewModel() {

    private val locationMutableLiveData: MutableLiveData<ArrayList<Location>> =
        MutableLiveData()
    val locationLiveData: LiveData<ArrayList<Location>>
        get() = locationMutableLiveData

    private val loadingMutableLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    private val errorMutableLiveData: MutableLiveData<String> =
        MutableLiveData()
    val errorLiveData: LiveData<String>
        get() = errorMutableLiveData

    fun getLocation(page: Int) {

        locationInteractor.getLocationArray(page, {
            when (it) {
                is SuccessList -> {
                    loadingMutableLiveData.value = true
                    val result: ArrayList<Location> = it.value
                    locationMutableLiveData.value = result
                    loadingMutableLiveData.value = false
                }
                is ErrorList -> errorMutableLiveData.value = "Необходим доступ к интенету"
            }
        },{
            LocationListFragment.allSizePage = it.value
        })
    }

    fun getLocation(page: Int, filter: SearchLocation) {
        locationInteractor.getLocationFilter(page, filter, {
            loadingMutableLiveData.value = true
            val result: ArrayList<Location> = it.value
            locationMutableLiveData.value = result
            loadingMutableLiveData.value = false
        },{
            LocationListFragment.filterSizePage = it.value
        })
    }

    fun updateList() {
        locationMutableLiveData.value?.clear()
    }
}