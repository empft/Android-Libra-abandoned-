package com.example.libraandroid.domain.account

import retrofit2.http.GET

interface AccountManagementService {
    @GET
    suspend fun logout()
}