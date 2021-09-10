package com.example.domain.interactors

import com.example.domain.entity.Episode
import com.example.domain.entity.Location
import com.example.domain.entity.Personage
import com.example.domain.repository.IRepositoryDetails
import com.example.domain.repository.SuccessDetails
import javax.inject.Inject

interface IDetailInteractor {
    fun getPersonageDetails(id :Int, callback : (result : SuccessDetails<Personage>) -> Unit)
    fun getLocationDetails(id :Int, callback : (result : SuccessDetails<Location>) -> Unit)
    fun getEpisodeDetails(id :Int, callback : (result : SuccessDetails<Episode>) -> Unit)
}

class DetailIInteractor @Inject constructor(private val repositoryDetails: IRepositoryDetails) : IDetailInteractor{
    override fun getPersonageDetails(id: Int, callback: (result: SuccessDetails<Personage>) -> Unit)
    = repositoryDetails.getPersonageDetails(id, callback)

    override fun getLocationDetails(id: Int, callback: (result: SuccessDetails<Location>) -> Unit)
    = repositoryDetails.getLocationDetails(id, callback)

    override fun getEpisodeDetails(id: Int, callback: (result: SuccessDetails<Episode>) -> Unit)
    = repositoryDetails.getEpisodeDetails(id, callback)
}