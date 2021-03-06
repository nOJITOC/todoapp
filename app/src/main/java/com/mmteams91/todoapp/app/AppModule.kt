package com.mmteams91.todoapp.app

import android.app.Application
import android.content.Context
import com.mmteams91.todoapp.common.data.PersistenceModule
import com.mmteams91.todoapp.common.data.RepositoriesModule
import com.mmteams91.todoapp.common.data.network.NetworkModule
import com.mmteams91.todoapp.common.presentation.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * This is a Dagger module. We use this to bind our Application class as a Context in the AppComponent
 * By using Dagger Android we do not need to pass our Application instance to any module,
 * we simply need to expose our Application as Context.
 * One of the advantages of Dagger.Android is that your
 * Application & Activities are provided into your graph for you.
 * [ ].
 */
@Module(includes = [
    ViewModelModule::class,
    PersistenceModule::class,
    NetworkModule::class,
    RepositoriesModule::class
])
abstract class AppModule {
    //expose Application as an injectable context

    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context
}

