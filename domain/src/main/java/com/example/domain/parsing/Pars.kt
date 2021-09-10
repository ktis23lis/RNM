package com.example.domain.parsing

import com.example.domain.entity.*

object Pars {
    fun addPersonageArray(personageFrom: PersonageOpen, personageIn: ArrayList<Personage>) {
        for (i in personageFrom.personageList) {
            val personageId = i.personageId
            val personageImage = i.personageImage
            val personageName = i.personageName
            val personageSpecies = i.personageSpecies
            val personageStatus = i.personageStatus
            val personageGender = i.personageGender
            val personageOrigin = i.personageOriginName
            val personageLocation = i.personageLocation
            val personageEpisode = i.personageEpisode
            personageIn.add(
                Personage(
                    personageId,
                    personageImage,
                    personageName,
                    personageSpecies,
                    personageStatus,
                    personageGender,
                    personageOrigin,
                    personageLocation,
                    personageEpisode
                )
            )
        }
    }

    fun addLocationArray(locationFrom: LocationOpen, locationIn: ArrayList<Location>) {
        for (i in locationFrom.locationList) {
            val locationId = i.locationId
            val locationName = i.locationName
            val locationType = i.locationType
            val locationDimension = i.locationDimension
            val locationUrl = i.locationUrl
            val locationResidents = i.locationResidents
            locationIn.add(
                Location(
                    locationId,
                    locationName,
                    locationType,
                    locationDimension,
                    locationUrl,
                    locationResidents
                )
            )
        }
    }

    fun addEpisodeArray(episodeFrom: EpisodeOpen, episodeIn: ArrayList<Episode>) {
        for (i in episodeFrom.episodeList) {
            val episodeId = i.episodeId
            val episodeName = i.episodeName
            val episode = i.episode
            val episodeAirFate = i.episodeAirFate
            val episodeCharacter = i.episodeCharacters
            episodeIn.add(
                Episode(
                    episodeId,
                    episodeName,
                    episode,
                    episodeAirFate,
                    episodeCharacter
                )
            )
        }
    }
}