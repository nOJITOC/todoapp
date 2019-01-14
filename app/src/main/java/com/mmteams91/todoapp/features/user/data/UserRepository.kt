package com.mmteams91.todoapp.features.user.data

import android.content.SharedPreferences
import com.mmteams91.todoapp.common.extensions.applyString
import com.mmteams91.todoapp.common.extensions.fromJson
import com.mmteams91.todoapp.common.extensions.toJson
import com.mmteams91.todoapp.features.user.User
import com.mmteams91.todoapp.features.user.auth.UserFromResponseTransformer
import com.squareup.moshi.Moshi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val moshi: Moshi,
        private val authApi: AuthApi,
        private val userFromResponseTransformer: UserFromResponseTransformer
) : IUserRepository {
    override var user: User?
        get() = sharedPreferences.getString(USER_PREFERENCE_KEY, null)
                ?.let { moshi.fromJson(it) }
        set(value) {
            sharedPreferences.applyString(USER_PREFERENCE_KEY, moshi.toJson(value))
            sharedPreferences.applyString(WEB_SOCKET_URL_KEY, value?.websocketUrl)
            sharedPreferences.applyString(TOKEN_KEY, value?.token)
        }
    override val token: String?
        get() = sharedPreferences.getString(TOKEN_KEY, null)

    override fun auth(email: String, password: String): Single<User> {
        return authApi.auth(email, password).map { userFromResponseTransformer.transform(it) }
    }
}

const val WEB_SOCKET_URL_KEY = "web_socket"
const val TOKEN_KEY = "token"
const val USER_PREFERENCE_KEY = "user"