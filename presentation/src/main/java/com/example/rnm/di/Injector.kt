package com.example.rnm.di

import android.content.Context
import com.example.rnm.di.component.ApplicationComponent
import com.example.rnm.di.component.DaggerApplicationComponent
import com.example.rnm.di.modules.AppModule
import com.example.rnm.feature.episode.di.EpisodeDetailComponent
import com.example.rnm.feature.episode.di.EpisodeListComponent
import com.example.rnm.feature.location.di.LocationDetailComponent
import com.example.rnm.feature.location.di.LocationListComponent
import com.example.rnm.feature.personage.di.PersonageDetailComponent
import com.example.rnm.feature.personage.di.PersonageListComponent

object Injector {

    lateinit var applicationComponent: ApplicationComponent
    private set

    val personageFragmentComponent: PersonageListComponent
        get() {
            return applicationComponent.personageListFragmentComponent
        }

    val personageDetailFragmentComponent: PersonageDetailComponent
        get() {
            return applicationComponent.personageDetailFragmentComponent
        }

    val locationListFragmentComponent: LocationListComponent
        get() {
            return applicationComponent.locationListFragmentComponent
        }

    val locationDetailFragmentComponent: LocationDetailComponent
        get() {
            return applicationComponent.locationDetailFragmentComponent
        }

    val episodeListFragmentComponent: EpisodeListComponent
        get() {
            return applicationComponent.episodeListFragmentComponent
        }

    val episodeDetailFragmentComponent: EpisodeDetailComponent
        get() {
            return applicationComponent.episodeDetailFragmentComponent
        }

    internal fun initApplicationComponent(context: Context){
        applicationComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}