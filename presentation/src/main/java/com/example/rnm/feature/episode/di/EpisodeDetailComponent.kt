package com.example.rnm.feature.episode.di

import androidx.lifecycle.ViewModel
import com.example.rnm.di.annotation.ViewModelKey
import com.example.rnm.feature.episode.detail.EpisodeDetailFragment
import com.example.rnm.feature.episode.detail.EpisodeDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [EpisodeDetailFragmentModule::class])
interface EpisodeDetailComponent {
    fun inject(episodeDetailFragment: EpisodeDetailFragment)
}

@Module
interface EpisodeDetailFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailViewModel::class)
    fun bindViewModel(listViewModel: EpisodeDetailViewModel): ViewModel
}