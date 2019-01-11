package com.mmteams91.todoapp.core.data.network

import com.mmteams91.todoapp.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module(includes = [ApiModule::class])
class NetworkModule {


    @Singleton
    @Provides
    @NotNeedAuth
    fun provideOkHttpClientIfNotNeedAuth(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideScheduler(): Scheduler = Schedulers.newThread()

    @Singleton
    @Provides
    @NotNeedAuth
    fun provideRetrofitIfNotNeedAuth(@NotNeedAuth okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
            .build()

}
