package repository

import android.util.Log
import com.example.data.database.dao.DatabaseStorage
import com.example.domain.convector.EntityForSingleEpisode
import com.example.domain.convector.EntityForSingleLocation
import com.example.domain.convector.EntityForSinglePersonage
import com.example.domain.entity.Episode
import com.example.domain.entity.Location
import com.example.domain.entity.Personage
import com.example.domain.repository.IRepositoryDetails
import com.example.domain.repository.SuccessDetails
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import network.RickAndMortyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryDetail @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val databaseStorage: DatabaseStorage
) : IRepositoryDetails {

    override fun getPersonageDetails(
        id: Int,
        callback: (result: SuccessDetails<Personage>) -> Unit
    ) {
        rickAndMortyApi.getPersonageDetail(id)
            .map {
                Personage(
                    it.personageId,
                    it.personageImage,
                    it.personageName,
                    it.personageSpecies,
                    it.personageStatus,
                    it.personageGender,
                    it.personageOriginName,
                    it.personageLocation,
                    it.personageEpisode
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { personage -> callback(SuccessDetails(personage)) },
                { error ->
                    Log.e("AAA", "$error Error")
                    databaseStorage.rickAndMortyDao.getPersonageById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { successDao ->
                                callback(SuccessDetails(EntityForSinglePersonage.conv(successDao)))
                            },
                            { e -> Log.e("AAA", "$e Error DAO") })
                }
            )
    }

    override fun getLocationDetails(id: Int, callback: (result: SuccessDetails<Location>) -> Unit) {
        rickAndMortyApi.getLocationDetail(id)
            .map {
                Location(
                    it.locationId,
                    it.locationName,
                    it.locationType,
                    it.locationDimension,
                    it.locationUrl,
                    it.locationResidents
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { location -> callback(SuccessDetails(location)) },
                { error ->
                    Log.e("AAA", "$error Error")
                    databaseStorage.rickAndMortyDao.getLocationById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { successDao ->
                                callback(SuccessDetails(EntityForSingleLocation.conv(successDao)))
                            },
                            { e ->Log.e("AAA", "$e Error DAO") })
                }
            )
    }

    override fun getEpisodeDetails(id: Int, callback: (result: SuccessDetails<Episode>) -> Unit) {
        rickAndMortyApi.getEpisodeDetail(id)
            .map {
                Episode(
                    it.episodeId,
                    it.episodeName,
                    it.episode,
                    it.episodeAirFate,
                    it.episodeCharacters
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { episode -> callback(SuccessDetails(episode)) },
                { error ->
                    Log.e("AAA", "$error Error")
                    databaseStorage.rickAndMortyDao.getEpisodeById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { successDao ->
                                callback(SuccessDetails(EntityForSingleEpisode.conv(successDao)))
                            },
                            {e -> Log.e("AAA", "$e Error DAO") })
                }
            )
    }
}