package com.example.data.database.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.entity.EpisodeEntity
import com.example.domain.entity.LocationEntity
import com.example.domain.entity.PersonageEntity

@Database(
    entities = [PersonageEntity::class, LocationEntity::class, EpisodeEntity::class],
    version = 1
)
abstract class DatabaseStorage : RoomDatabase() {

    abstract val rickAndMortyDao: RickAndMortyDao

    companion object {
        const val RICK_AND_MORTY_DATA_BASE = "RICK_AND_MORTY_DATA_BASE"
    }
}