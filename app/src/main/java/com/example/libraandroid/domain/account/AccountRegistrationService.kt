package com.example.libraandroid.domain.account

import retrofit2.Response
import retrofit2.http.*

interface AccountRegistrationService {
    @GET("users/exist")
    suspend fun exist(@Query("username") username: String)

    @FormUrlEncoded
    @POST("users/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("displayName") displayName: String,
        @Field("password") password: String,
        @Field("email") email: String
    )

    @FormUrlEncoded
    @POST("users/invitation")
    suspend fun createInvitation(@Field("email") email: String)

    @FormUrlEncoded
    @POST("users/register-with-invitation")
    suspend fun registerWithInvitation(
        @Field("username") username: String,
        @Field("displayName") displayName: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("invitationEmail") invitationEmail: String,
        @Field("invitationCode") invitationCode: String
    )
}