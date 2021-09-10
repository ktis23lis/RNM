package com.example.rnm.feature.episode.di

import androidx.lifecycle.ViewModel
import com.example.rnm.di.annotation.ViewModelKey
import com.example.rnm.feature.episode.list.EpisodeListFragment
import com.example.rnm.feature.episode.list.EpisodeListViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [EpisodeFragmentModule::class])
interface EpisodeListComponent {
    fun inject(episodeListFragment: EpisodeListFragment)
}

@Module
interface EpisodeFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(EpisodeListViewModel::class)
    fun bindViewModel(listViewModel: EpisodeListViewModel): ViewModel
}