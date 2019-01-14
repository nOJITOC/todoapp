package com.mmteams91.todoapp.common.data

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.mmteams91.todoapp.BuildConfig
import com.mmteams91.todoapp.common.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun providePreferences(context: Context): SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    @Synchronized
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                //todo move to const
                "db_name"
        ).build()
    }


}

private const val PREFERENCE_NAME = BuildConfig.APPLICATION_ID + ".Preferences"
