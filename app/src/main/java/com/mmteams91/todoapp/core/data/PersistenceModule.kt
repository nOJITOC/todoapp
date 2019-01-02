package com.mmteams91.todoapp.core.data

import android.content.Context
import android.content.SharedPreferences
import com.mmteams91.todoapp.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun providePreferences(context: Context): SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

}

private const val PREFERENCE_NAME = BuildConfig.APPLICATION_ID + ".Preferences"
