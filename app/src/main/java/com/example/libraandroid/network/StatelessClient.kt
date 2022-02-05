package com.example.libraandroid.network

import com.example.libraandroid.domain.account.login.AccountLoginRequest
import com.example.libraandroid.domain.account.recovery.AccountRecoveryRequest
import com.example.libraandroid.domain.account.registration.AccountRegistrationRequest
import retrofit2.Retrofit

object StatelessClient {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConstant.ApiBaseUrl)
            .build()
    }

    val recovery: AccountRecoveryRequest by lazy {
        retrofit.create(AccountRecoveryRequest::class.java)
    }

    val login: AccountLoginRequest by lazy {
        retrofit.create(AccountLoginRequest::class.java)
    }

    val registration: AccountRegistrationRequest by lazy {
        retrofit.create(AccountRegistrationRequest::class.java)
    }
}