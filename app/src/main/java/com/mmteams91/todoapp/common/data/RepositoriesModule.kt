package com.mmteams91.todoapp.common.data

import com.mmteams91.todoapp.features.user.data.IUserRepository
import com.mmteams91.todoapp.features.user.data.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun provideUserRepository(userRepository: UserRepository):IUserRepository
}