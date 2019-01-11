package com.mmteams91.todoapp.core.data.network

import com.mmteams91.todoapp.features.user.data.AuthApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideAuthAPi(@NotNeedAuth retrofit: Retrofit): AuthApi = retrofit.create()

    private inline fun <reified T> Retrofit.create() = create(T::class.java)
}
