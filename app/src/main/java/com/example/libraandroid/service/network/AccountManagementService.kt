package com.example.libraandroid.service.network

import retrofit2.http.GET

interface AccountManagementService {
    @GET
    suspend fun logout()
}