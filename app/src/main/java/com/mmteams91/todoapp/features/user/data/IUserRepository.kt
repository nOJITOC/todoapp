package com.mmteams91.todoapp.features.user.data

import com.mmteams91.todoapp.features.user.User
import io.reactivex.Single

interface IUserRepository {


    var user: User?

    val accessToken: String?

    fun isAuthenticated(): Boolean = accessToken != null

    fun auth(email: String, password: String): Single<User>
}