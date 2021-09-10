package com.example.rnm.di.modules

import android.content.Context
import androidx.room.Room
import com.example.data.database.dao.DatabaseStorage
import com.example.domain.interactors.DetailIInteractor
import com.example.domain.interactors.IDetailInteractor
import com.example.domain.interactors.IListInteractor
import com.example.domain.interactors.ListIInteractor
import com.example.domain.repository.IRepositoryDetails
import com.example.domain.repository.IRepositoryList
import dagger.Binds
import dagger.Module
import dagger.Provides
import repository.RepositoryDetail
import repository.RepositoryList
import javax.inject.Singleton

@Module(includes = [AppModule.InnerAppModule::class])
class AppModule(private  val context: Context) {

    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideDatabaseStorage() = Room.databaseBuilder(
            context,
            DatabaseStorage::class.java,
            DatabaseStorage.RICK_AND_MORTY_DATA_BASE
        ).build()


    @Module
    interface InnerAppModule{

        @Binds
        @Singleton
        fun bindListRepository(repositoryRetrofitList: RepositoryList): IRepositoryList

        @Binds
        @Singleton
        fun bindDetailRepository(repositoryRetrofitDetails: RepositoryDetail): IRepositoryDetails

        @Binds
        @Singleton
        fun bindListInteracrot(interactorList : ListIInteractor): IListInteractor

        @Binds
        @Singleton
        fun bindDetailInteractor(interactorDetail : DetailIInteractor): IDetailInteractor
    }
}