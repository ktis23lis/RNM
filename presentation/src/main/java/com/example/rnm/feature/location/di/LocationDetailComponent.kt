package com.example.rnm.feature.location.di

import androidx.lifecycle.ViewModel
import com.example.rnm.di.annotation.ViewModelKey
import com.example.rnm.feature.location.detail.LocationDetailFragment
import com.example.rnm.feature.location.detail.LocationDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [LocationDetailFragmentModule::class])
interface LocationDetailComponent {
    fun inject(locationDetailFragment: LocationDetailFragment)
}

@Module
interface LocationDetailFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailViewModel::class)
    fun bindViewModel(listViewModel: LocationDetailViewModel): ViewModel
}