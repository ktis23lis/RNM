package com.example.rnm.feature.personage.di

import androidx.lifecycle.ViewModel
import com.example.rnm.di.annotation.ViewModelKey
import com.example.rnm.feature.personage.detail.PersonageDetailFragment
import com.example.rnm.feature.personage.detail.PersonageDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [PersonageDetailFragmentModule::class])
interface PersonageDetailComponent {

    fun inject(personageDetailFragment: PersonageDetailFragment)
}

@Module
interface PersonageDetailFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(PersonageDetailViewModel::class)
    fun bindViewModel(listViewModel: PersonageDetailViewModel): ViewModel
}