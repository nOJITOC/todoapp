package com.mmteams91.todoapp.common.data.network

import com.mmteams91.todoapp.features.user.data.IUserRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
        private val userRepository: IUserRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url().newBuilder().addQueryParameter("token", userRepository.token).build()
        return chain.proceed(chain.request().newBuilder().url(newUrl).build())
    }
}