package com.example.domain.convector

import com.example.domain.entity.Personage
import com.example.domain.entity.PersonageEntity
import com.example.domain.entity.PersonageOrigin

object PersonageForEntity : BaseConvector<ArrayList<Personage>, ArrayList<PersonageEntity>> {
    override fun conv(type: ArrayList<Personage>): ArrayList<PersonageEntity> {
        var personageEntity = ArrayList<PersonageEntity>()
        for (i in type) {
            personageEntity.add(
                PersonageEntity(
                    personageId = i.personageId,
                    personageImage = i.personageImage,
                    personageName = i.personageName,
                    personageSpecies = i.personageSpecies,
                    personageStatus = i.personageStatus,
                    personageGender = i.personageGender,
                    personageOriginName = i.personageOriginName.name,
                    personagePersonageUrl = i.personageOriginName.url,
                    personageLocation = i.personageLocation.name,
                    personageLocationUrl = i.personageLocation.url
                )
            )
        }
        return personageEntity
    }
}

object EntityForPersonage : BaseConvector<List<PersonageEntity>, ArrayList<Personage>> {
    override fun conv(type: List<PersonageEntity>): ArrayList<Personage> {
        var personage = ArrayList<Personage>()
        for (i in type) {
            personage.add(
                Personage(
                    personageId = i.personageId,
                    personageImage = i.personageImage,
                    personageName = i.personageName,
                    personageSpecies = i.personageSpecies,
                    personageStatus = i.personageStatus,
                    personageGender = i.personageGender,
                    personageOriginName = PersonageOrigin(
                        i.personageOriginName,
                        i.personagePersonageUrl
                    ),
                    personageLocation = PersonageOrigin(
                        i.personageLocation,
                        i.personageLocationUrl
                    ),
                    personageEpisode = null
                )
            )
        }
        return personage
    }
}

object EntityForSinglePersonage : BaseConvector<PersonageEntity, Personage> {
    override fun conv(type: PersonageEntity): Personage {
        val personage = Personage(
            personageId = type.personageId,
            personageImage = type.personageImage,
            personageName = type.personageName,
            personageSpecies = type.personageSpecies,
            personageStatus = type.personageStatus,
            personageGender = type.personageGender,
            personageOriginName = PersonageOrigin(
                type.personageOriginName,
                type.personagePersonageUrl
            ),
            personageLocation = PersonageOrigin(type.personageLocation, type.personageLocationUrl),
            personageEpisode = null
        )
        return personage
    }
}