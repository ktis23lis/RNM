package repository

import android.util.Log
import android.widget.Toast
import com.example.data.database.dao.DatabaseStorage
import com.example.domain.convector.*
import com.example.domain.entity.*
import com.example.domain.parsing.Pars
import com.example.domain.repository.ErrorList
import com.example.domain.repository.IRepositoryList
import com.example.domain.repository.RepositoryListResult
import com.example.domain.repository.SuccessList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import network.RickAndMortyApi
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryList @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val databaseStorage: DatabaseStorage
) : IRepositoryList {

    private var personageData = ArrayList<Personage>()
    private var personageDataDB = ArrayList<PersonageEntity>()
    private var personagePage= 0
    private var locationData = ArrayList<Location>()
    private var locationDataDB = ArrayList<LocationEntity>()
    private var locationPage= 0
    private var episodeData = ArrayList<Episode>()
    private var episodeDataDB = ArrayList<EpisodeEntity>()
    private var episodePage= 0


    override fun getPersonageArray(
        page: Int?,
        callback: (result: RepositoryListResult<ArrayList<Personage>>) -> Unit,
        callbackPage : (result : SuccessList<Int>) -> Unit
    ) {
        rickAndMortyApi.getAllPersonage(page = page)
            .map { Pars.addPersonageArray(it, personageData)
                personagePage = it.info.pages
            }
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success ->
                    callback(SuccessList(personageData))
                    callbackPage(SuccessList(personagePage))
                    personageDataDB = PersonageForEntity.conv(personageData)
                    databaseStorage.rickAndMortyDao.saveAllPersonage(personageDataDB)
                        .subscribeOn(Schedulers.computation())
                        .subscribe(object : CompletableObserver {
                            override fun onSubscribe(d: Disposable?) {}
                            override fun onComplete() {}
                            override fun onError(e: Throwable?) {
                            }
                        })
                }, { error ->
                    Log.e("AAA", "$error Error API")
                    databaseStorage.rickAndMortyDao.getAllPersonage()
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { successDao ->
                                callback(SuccessList(EntityForPersonage.conv(successDao)))
                                if (successDao.isNullOrEmpty()){
                                    callback(ErrorList(RuntimeException("Error loading")))
                                }

                            },
                            { e ->
                                Log.e("AAA", "$e Error DAO 1.1")
                            })
                })
    }

    override fun getPersonageFilter(
        page: Int?,
        filter: SearchPersonage?,
        callback: (result: SuccessList<ArrayList<Personage>>) -> Unit,
        callbackPage : (result : SuccessList<Int>) -> Unit
    ) {
        rickAndMortyApi.getPersonageFilter(
            page = page,
            status = filter?.status,
            gender = filter?.gender,
            species = filter?.species
        ).map {
            Pars.addPersonageArray(it, personageData)
            personagePage = it.info.pages
        }
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success -> callback(SuccessList(personageData))
                callbackPage(SuccessList(personagePage))},
                { error ->
                    Log.e("AAA", "$error Error")
                    databaseStorage.rickAndMortyDao.getPersonageFilter(
                        status = filter?.status,
                        gender = filter?.gender,
                        species = filter?.species
                    )
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { successDao ->
                                callback(SuccessList(EntityForPersonage.conv(successDao)))
                                Log.e("AAA", "${Thread.currentThread().name} Success DAO")
                            },
                            { e -> Log.e("AAA", "$e Error DAO 1.2") })
                }
            )
    }

    override fun getLocationArray(
        page: Int,
        callback: (result: RepositoryListResult<ArrayList<Location>>) -> Unit,
        callbackPage : (result : SuccessList<Int>) -> Unit
    ) {
        rickAndMortyApi.getAllLocation(page = page)
            .map { Pars.addLocationArray(it, locationData)
            locationPage = it.info.pages}
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success ->
                    callback(SuccessList(locationData))
                    callbackPage(SuccessList(locationPage))
                    locationDataDB = LocationForEntity.conv(locationData)
                    databaseStorage.rickAndMortyDao.saveAllLocation(locationDataDB)
                        .subscribeOn(Schedulers.computation())
                        .subscribe(object : CompletableObserver {
                            override fun onSubscribe(d: Disposable?) {}
                            override fun onComplete() {}
                            override fun onError(e: Throwable?) {}
                        })
                },
                { error ->
                    Log.e("AAA", "$error Error")
                    databaseStorage.rickAndMortyDao.getAllLocation()
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { successDao ->
                                callback(SuccessList(EntityForLocation.conv(successDao)))
                                if (successDao.isNullOrEmpty()){
                                    callback(ErrorList(RuntimeException("Error loading")))
                                }
                                Log.e("AAA", "${Thread.currentThread().name} Success DAO")
                            },
                            { e -> Log.e("AAA", "$e Error DAO 2.1") })
                }
            )
    }

    override fun getLocationFilter(
        page: Int?,
        filter: SearchLocation?,
        callback: (result: SuccessList<ArrayList<Location>>) -> Unit,
        callbackPage : (result : SuccessList<Int>) -> Unit
    ) {
        rickAndMortyApi.getLocationFilter(
            page = page,
            type = filter?.type
        )
            .map {
                Pars.addLocationArray(it, locationData)
                locationPage = it.info.pages
            }
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success -> callback(SuccessList(locationData))
                    callbackPage(SuccessList(locationPage))},
                { error ->
                    Log.e("AAA", "$error Error")
                    databaseStorage.rickAndMortyDao.getLocationFilter(type = filter?.type)
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { successDao ->
                                callback(SuccessList(EntityForLocation.conv(successDao)))
                                Log.e("AAA", "${Thread.currentThread().name} Success DAO")
                            },
                            { e -> Log.e("AAA", "$e Error DAO 2.2") }

                        )
                }
            )
    }

    override fun getEpisodeArray(
        page: Int,
        callback: (result: RepositoryListResult<ArrayList<Episode>>) -> Unit,
        callbackPage : (result : SuccessList<Int>) -> Unit
    ) {
        rickAndMortyApi.getAllEpisode(page = page)
            .map {
                Pars.addEpisodeArray(it, episodeData)
                episodePage = it.info.pages
            }
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success ->
                    callback(SuccessList(episodeData))
                    callbackPage(SuccessList(episodePage))
                    episodeDataDB = EpisodeForEntity.conv(episodeData)
                    databaseStorage.rickAndMortyDao.saveAllEpisode(episodeDataDB)
                        .subscribeOn(Schedulers.computation())
                        .subscribe(object : CompletableObserver {
                            override fun onSubscribe(d: Disposable?) {}
                            override fun onComplete() {}
                            override fun onError(e: Throwable?) {}
                        })
                },
                { error ->
                    Log.e("AAA", "$error Error ")

                    databaseStorage.rickAndMortyDao.getAllEpisode()
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { successDao ->
                                callback(SuccessList(EntityForEpisode.conv(successDao)))
                                if (successDao.isNullOrEmpty()){
                                    callback(ErrorList(RuntimeException("Error loading")))
                                }
                                Log.e("AAA", "${Thread.currentThread().name} Success DAO")
                            },
                            { e -> Log.e("AAA", "$e Error DAO 3.1") }
                        )
                }
            )
    }

    override fun getEpisodeFilter(
        page: Int?,
        filter: SearchEpisode?,
        callback: (result: SuccessList<ArrayList<Episode>>) -> Unit,
        callbackPage : (result : SuccessList<Int>) -> Unit
    ) {
        rickAndMortyApi.getEpisodeFilter(
            page = page,
            season = filter?.season
        )
            .map {
                Pars.addEpisodeArray(it, episodeData)
                episodePage = it.info.pages
            }
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success -> callback(SuccessList(episodeData))
                callbackPage(SuccessList(episodePage))
                },
                { error ->
                    Log.e("AAA", "$error Error")
                    databaseStorage.rickAndMortyDao.getEpisodeFilter(
                        if (filter?.season.isNullOrEmpty()) filter?.season else "%${filter?.season}%"
                    )
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { successDao ->
                                callback(SuccessList(EntityForEpisode.conv(successDao)))
                                Log.e("AAA", "${Thread.currentThread().name} Success DAO ${filter?.season}  ${successDao}")
                            },
                            { e -> Log.e("AAA", "$e Error DAO 3.2") }
                        )
                }
            )
    }
}