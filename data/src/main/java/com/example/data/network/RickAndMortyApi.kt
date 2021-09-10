package network

import com.example.domain.entity.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("/api/episode")
    fun getAllEpisode(@Query("page") page: Int): Single<EpisodeOpen>

    @GET("/api/location")
    fun getAllLocation(@Query("page") page: Int): Single<LocationOpen>

    @GET("/api/character")
    fun getAllPersonage(@Query("page") page: Int?): Single<PersonageOpen>

    @GET("/api/location")
    fun getLocationFilter(
        @Query("page") page: Int?,
        @Query("type") type: String?,
    ): Single<LocationOpen>

    @GET("/api/episode")
    fun getEpisodeFilter(
        @Query("page") page: Int?,
        @Query("episode") season: String?
    ): Single<EpisodeOpen>

    @GET("/api/character")
    fun getPersonageFilter(
        @Query("page") page: Int?,
        @Query("status") status: String?,
        @Query("gender") gender: String?,
        @Query("species") species: String?
    ): Single<PersonageOpen>

    @GET("/api/episode/{id}")
    fun getEpisodeDetail(@Path("id") id: Int): Observable<Episode>

    @GET("/api/location/{id}")
    fun getLocationDetail(@Path("id") id: Int): Observable<Location>

    @GET("/api/character/{id}")
    fun getPersonageDetail(@Path("id") id: Int): Observable<Personage>
}