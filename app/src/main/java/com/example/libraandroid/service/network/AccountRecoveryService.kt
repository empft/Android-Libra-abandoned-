package com.example.libraandroid.service.network

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountRecoveryService {
    @FormUrlEncoded
    @POST("users/forget-username")
    suspend fun forgetUsername(@Field("email") email: String)

    @FormUrlEncoded
    @POST("users/forget-password")
    suspend fun forgetPassword(
        @Field("username") username: String,
        @Field("email") email: String
    )

    @FormUrlEncoded
    @POST("users/reset-password")
    suspend fun resetPassword(
        @Field("token") token: String,
        @Field("password") password: String
    )
}