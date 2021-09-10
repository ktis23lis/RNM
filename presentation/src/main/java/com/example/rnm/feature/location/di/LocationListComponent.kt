package com.example.rnm.feature.location.di

import androidx.lifecycle.ViewModel
import com.example.rnm.di.annotation.ViewModelKey
import com.example.rnm.feature.location.list.LocationListFragment
import com.example.rnm.feature.location.list.LocationListViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [LocationFragmentModule::class])
interface LocationListComponent {
    fun inject(locationListFragment: LocationListFragment)
}

@Module
interface LocationFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(LocationListViewModel::class)
    fun bindViewModel(listViewModel: LocationListViewModel): ViewModel
}