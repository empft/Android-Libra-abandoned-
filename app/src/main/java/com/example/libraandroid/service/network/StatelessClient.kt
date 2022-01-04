package com.example.libraandroid.service.network

import retrofit2.Retrofit

object StatelessClient {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConstant.ApiBaseUrl)
            .build()
    }

    val recoveryService: AccountRecoveryService by lazy {
        retrofit.create(AccountRecoveryService::class.java)
    }

    val loginService: AccountLoginService by lazy {
        retrofit.create(AccountLoginService::class.java)
    }

    val registrationService: AccountRegistrationService by lazy {
        retrofit.create(AccountRegistrationService::class.java)
    }
}