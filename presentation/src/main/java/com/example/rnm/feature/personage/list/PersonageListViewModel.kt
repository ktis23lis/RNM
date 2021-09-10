package com.example.rnm.feature.personage.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.entity.Personage
import com.example.domain.entity.SearchPersonage
import com.example.domain.interactors.IListInteractor
import com.example.domain.repository.ErrorList
import com.example.domain.repository.SuccessList
import com.example.rnm.feature.base.BaseViewModel
import javax.inject.Inject

class PersonageListViewModel @Inject constructor(
    private val personageInteractor: IListInteractor
) : BaseViewModel() {

    private val personageMutableLiveData: MutableLiveData<ArrayList<Personage>> =
        MutableLiveData()
    val personageLiveData: LiveData<ArrayList<Personage>>
        get() = personageMutableLiveData

    private val loadingMutableLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    private val errorMutableLiveData: MutableLiveData<String> =
        MutableLiveData()
    val errorLiveData: LiveData<String>
        get() = errorMutableLiveData


    fun getPersonage(page: Int) {

        personageInteractor.getPersonageArray(page, {
            when (it) {
                is SuccessList -> {
                    loadingMutableLiveData.value = true
                    val result: ArrayList<Personage> = it.value
                    personageMutableLiveData.value = result
                    loadingMutableLiveData.value = false
                }
                is ErrorList -> errorMutableLiveData.value = "Необходим доступ к интенету"
            }
        },{
            PersonageListFragment.allSizePage = it.value
        })
    }

    fun getPersonage(page: Int?, filter: SearchPersonage) {
        personageInteractor.getPersonageFilter(page, filter, {
            loadingMutableLiveData.value = true
            val result: ArrayList<Personage> = it.value
            personageMutableLiveData.value = result
            loadingMutableLiveData.value = false
        },{
            PersonageListFragment.filterSizePage = it.value
        })
    }

    fun updateList() {
        personageMutableLiveData.value?.clear()
    }
}