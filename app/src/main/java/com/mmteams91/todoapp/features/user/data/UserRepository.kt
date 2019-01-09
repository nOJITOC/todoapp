package com.mmteams91.todoapp.features.user.data

import android.content.SharedPreferences
import com.mmteams91.todoapp.core.extensions.applyString
import com.mmteams91.todoapp.core.extensions.fromJson
import com.mmteams91.todoapp.core.extensions.toJson
import com.mmteams91.todoapp.features.user.User
import com.squareup.moshi.Moshi
import javax.inject.Inject

class UserRepository @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val moshi: Moshi
) : IUserRepository {
    override var user: User?
        get() = sharedPreferences.getString(USER_PREFERENCE_KEY, null)
                ?.let { moshi.fromJson(it) }
        set(value) {
            sharedPreferences.applyString(USER_PREFERENCE_KEY, moshi.toJson(value))
            sharedPreferences.applyString(WEB_SOCKET_URL_KEY, value?.websocketUrl)
            sharedPreferences.applyString(TOKEN_KEY, value?.token)
        }
    override val accessToken: String?
        get() = sharedPreferences.getString(TOKEN_KEY, null)
}

const val WEB_SOCKET_URL_KEY = "web_socket"
const val TOKEN_KEY = "token"
const val USER_PREFERENCE_KEY = "user"