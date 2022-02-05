package com.example.libraandroid.domain.account.registration

import com.example.libraandroid.domain.account.session.AccountSessionUtil
import com.example.libraandroid.miscellaneous.Either
import com.example.libraandroid.network.ResponseHandler
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import retrofit2.Response
import retrofit2.http.*

interface AccountRegistrationRequest {
    @GET("users/exist")
    suspend fun exist(@Query("username") username: String): Response<Boolean>

    @FormUrlEncoded
    @POST("users/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("displayName") displayName: String,
        @Field("password") password: String,
        @Field("email") email: String
    ): Response<Unit>

    @FormUrlEncoded
    @POST("users/invitation")
    suspend fun createInvitation(@Field("email") email: String): Response<Unit>

    @FormUrlEncoded
    @POST("users/register-with-invitation")
    suspend fun registerWithInvitation(
        @Field("username") username: String,
        @Field("displayName") displayName: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("invitationEmail") invitationEmail: String,
        @Field("invitationCode") invitationCode: String
    ): Response<Unit>
}

@Serializable
private data class NetworkRegistrationError(
    val username: String? = null,
    val displayName: String? = null,
    val password: String? = null,
    val email: String? = null,
    val invitationEmail: String? = null,
    val invitationCode: String? = null
) {
    fun toRegistrationError(): RegistrationError {
        return RegistrationError(
            username = this.username,
            displayName = this.displayName,
            password = this.password,
            email = this.email,
            invitationEmail = this.invitationEmail,
            invitationCode = this.invitationCode
        )
    }
}

class AccountRegistrationInteractor(
    private val network: AccountRegistrationRequest
) {
    suspend fun userExists(username: String): Either<Boolean, String> {
        val response = network.exist(username)
        return ResponseHandler.forge(String.serializer(), response)
    }

    /**
     * Send a network request to register and returns the session cookie
     */
    suspend fun register(
        username: String,
        displayName: String,
        password: String,
        email: String
    ): Either<String, RegistrationError> {
        val response = network.register(username, displayName, password, email)

        return when(val result = ResponseHandler.forgeError(NetworkRegistrationError.serializer(), response)) {
            is Either.Failure -> {
                Either.Failure(result.value.toRegistrationError())
            }
            is Either.Success -> {
                Either.Success(AccountSessionUtil.parseToken(result.value))
            }
        }
    }

    suspend fun createInvitation(email: String): Either<Unit, String> {
        val response = network.createInvitation(email)
        return ResponseHandler.forge(String.serializer(), response)
    }

    /**
     * Send a network request to register and returns the session cookie
     */
    suspend fun registerWithInvitation(
        username: String,
        displayName: String,
        password: String,
        email: String,
        invitationEmail: String,
        invitationCode: String
    ): Either<String, RegistrationError> {
        val response = network.registerWithInvitation(username, displayName, password, email, invitationEmail, invitationCode)

        return when(val result = ResponseHandler.forgeError(NetworkRegistrationError.serializer(), response)) {
            is Either.Failure -> {
                Either.Failure(result.value.toRegistrationError())
            }
            is Either.Success -> {
                Either.Success(AccountSessionUtil.parseToken(result.value))
            }
        }
    }
}