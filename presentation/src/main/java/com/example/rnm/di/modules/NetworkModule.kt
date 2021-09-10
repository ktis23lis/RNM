package com.example.rnm.di.modules


import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import network.RickAndMortyApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRickAndMortyApi(retrofit: Retrofit): RickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(gson : Gson, okHttpClient : OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()


    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideLogger() = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY.takeIf { BuildConfig.DEBUG } ?: HttpLoggingInterceptor.Level.NONE
    )

    private companion object{
        const val BASE_URL = "https://rickandmortyapi.com/"
    }
}

