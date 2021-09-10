package com.example.rnm.feature.personage.di

import androidx.lifecycle.ViewModel
import com.example.rnm.di.annotation.ViewModelKey
import com.example.rnm.feature.personage.list.PersonageListFragment
import com.example.rnm.feature.personage.list.PersonageListViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [PersonageFragmentModule::class])
interface PersonageListComponent {
    fun inject(personageListFragment: PersonageListFragment)
}

@Module
interface PersonageFragmentModule{

    @Binds
    @IntoMap
    @ViewModelKey(PersonageListViewModel::class)
    fun bindViewModel(listViewModel : PersonageListViewModel): ViewModel
}