package com.example.rnm.di.component

import android.content.Context
import com.example.rnm.di.modules.AppModule
import com.example.rnm.di.modules.NetworkModule
import com.example.rnm.di.modules.ViewModelModule
import com.example.rnm.feature.episode.di.EpisodeDetailComponent
import com.example.rnm.feature.episode.di.EpisodeListComponent
import com.example.rnm.feature.location.di.LocationDetailComponent
import com.example.rnm.feature.location.di.LocationListComponent
import com.example.rnm.feature.personage.di.PersonageDetailComponent
import com.example.rnm.feature.personage.di.PersonageListComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    val context: Context

    val personageListFragmentComponent : PersonageListComponent
    val personageDetailFragmentComponent : PersonageDetailComponent
    val locationListFragmentComponent : LocationListComponent
    val locationDetailFragmentComponent : LocationDetailComponent
    val episodeListFragmentComponent : EpisodeListComponent
    val episodeDetailFragmentComponent : EpisodeDetailComponent

}