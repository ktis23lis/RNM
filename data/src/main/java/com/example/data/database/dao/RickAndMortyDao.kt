package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.entity.EpisodeEntity
import com.example.domain.entity.LocationEntity
import com.example.domain.entity.PersonageEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RickAndMortyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllPersonage(personages: ArrayList<PersonageEntity>): Completable

    @Query("SELECT * FROM personage ")
    fun getAllPersonage(): Single<List<PersonageEntity>>

    @Query("SELECT * FROM personage WHERE (:status IS NULL OR status LIKE :status) AND (:gender IS NULL OR gender LIKE :gender) AND (:species IS NULL OR species LIKE :species)")
    fun getPersonageFilter(
        status: String?,
        gender: String?,
        species: String?
    ): Single<List<PersonageEntity>>

    @Query("SELECT * FROM personage WHERE id =:id")
    fun getPersonageById(id: Int): Single<PersonageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllLocation(location: ArrayList<LocationEntity>): Completable

    @Query("SELECT * FROM location")
    fun getAllLocation(): Single<List<LocationEntity>>

    @Query("SELECT * FROM location WHERE (:type IS NULL OR type LIKE :type)")
    fun getLocationFilter(type: String?): Single<List<LocationEntity>>

    @Query("SELECT * FROM location WHERE id =:id")
    fun getLocationById(id: Int): Single<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllEpisode(episode: ArrayList<EpisodeEntity>): Completable

    @Query("SELECT * FROM episode")
    fun getAllEpisode(): Single<List<EpisodeEntity>>

    @Query("SELECT * FROM episode WHERE (:season IS NULL OR episode LIKE :season)")
    fun getEpisodeFilter(season: String?): Single<List<EpisodeEntity>>

    @Query("SELECT * FROM episode WHERE id =:id")
    fun getEpisodeById(id: Int): Single<EpisodeEntity>
}