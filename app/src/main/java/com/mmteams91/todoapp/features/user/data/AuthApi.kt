package com.mmteams91.todoapp.features.user.data

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @POST("user/login")
    @FormUrlEncoded
    fun auth(@Field("email") email:String,@Field("password") password:String): Single<UserResponse>
}