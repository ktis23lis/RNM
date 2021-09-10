package com.example.domain.repository

import com.example.domain.entity.Episode
import com.example.domain.entity.Location
import com.example.domain.entity.Personage

interface IRepositoryDetails {

    fun getPersonageDetails(id :Int, callback : (result : SuccessDetails<Personage>) -> Unit)
    fun getLocationDetails(id :Int, callback : (result : SuccessDetails<Location>) -> Unit)
    fun getEpisodeDetails(id :Int, callback : (result : SuccessDetails<Episode>) -> Unit)
}