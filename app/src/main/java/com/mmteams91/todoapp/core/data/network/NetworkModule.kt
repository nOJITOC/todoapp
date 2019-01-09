package com.mmteams91.todoapp.core.data.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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

    @Provides
    fun provideScheduler() : Scheduler = Schedulers.newThread()

    @Singleton
    @Provides
    @NotNeedAuth
    fun provideRetrofitINotNeedAuth():Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

}
