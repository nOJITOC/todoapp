package com.mmteams91.todoapp.core.data.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
class NetworkModule {


    @Singleton
    @Provides
    @NotNeedAuth
    fun provideOkHttpClientIfNotNeedAuth(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

}
