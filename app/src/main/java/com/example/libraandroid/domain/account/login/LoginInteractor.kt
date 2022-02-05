package com.example.libraandroid.domain.account.login

import com.example.libraandroid.domain.account.session.AccountSessionUtil
import com.example.libraandroid.miscellaneous.Either
import com.example.libraandroid.network.ResponseHandler
import kotlinx.serialization.builtins.serializer
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountLoginRequest {
    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Response<Unit>
}

class LoginInteractor(
    private val network: AccountLoginRequest
) {
    /**
     * Send a network request to login and returns the session cookie
     */
    suspend fun login(username: String, password: String): Either<String, LoginError> {
        val response = network.login(username, password)

        return when(val result = ResponseHandler.forgeError(String.serializer(), response)) {
            is Either.Failure -> {
                Either.Failure(LoginError(result.value))
            }
            is Either.Success -> {
                Either.Success(AccountSessionUtil.parseToken(result.value))
            }
        }
    }
}

