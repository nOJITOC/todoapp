package com.mmteams91.todoapp.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class
])
interface AppComponent {


    fun baseViewModelComponent(): BaseViewModelComponent
    fun inject(baseActivity: AppActivity)


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
