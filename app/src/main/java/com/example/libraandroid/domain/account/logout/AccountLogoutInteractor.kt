package com.example.libraandroid.domain.account.logout

import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.GET

interface AccountLogoutRequest {
    @GET("users/logout")
    suspend fun logout(): Response<Unit>
}


class AccountLogoutInteractor(
    private val network: AccountLogoutRequest
) {
    suspend fun logout() {
        // TODO: error handling
        try {
            network.logout()
        } catch (e: HttpException) {

        }
    }
}