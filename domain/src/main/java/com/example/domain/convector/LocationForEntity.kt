package com.example.domain.convector

import com.example.domain.entity.Location
import com.example.domain.entity.LocationEntity

object LocationForEntity : BaseConvector<ArrayList<Location>, ArrayList<LocationEntity>> {
    override fun conv(type: ArrayList<Location>): ArrayList<LocationEntity> {
        var locationEntity = ArrayList<LocationEntity>()
        for (i in type) {
            locationEntity.add(
                LocationEntity(
                    locationId = i.locationId,
                    locationName = i.locationName,
                    locationType = i.locationType,
                    locationDimension = i.locationDimension,
                    locationUrl = i.locationUrl
                )
            )
        }
        return locationEntity
    }
}

object EntityForLocation : BaseConvector<List<LocationEntity>, ArrayList<Location>> {
    override fun conv(type: List<LocationEntity>): ArrayList<Location> {
        var location = ArrayList<Location>()
        for (i in type) {
            location.add(
                Location(
                    locationId = i.locationId,
                    locationName = i.locationName,
                    locationType = i.locationType,
                    locationDimension = i.locationDimension,
                    locationUrl = i.locationUrl,
                    locationResidents = null
                )
            )
        }
        return location
    }
}

object EntityForSingleLocation : BaseConvector<LocationEntity, Location> {
    override fun conv(type: LocationEntity): Location {
        val location = Location(
            locationId = type.locationId,
            locationName = type.locationName,
            locationType = type.locationType,
            locationDimension = type.locationDimension,
            locationUrl = type.locationUrl,
            locationResidents = null
        )
        return location
    }
}
