package com.example.libraandroid.service.session

import com.example.libraandroid.service.network.AccountManagementService
import retrofit2.HttpException

class AccountSessionLogoutManager(
    private val repo: AccountSessionRepository,
    private val network: AccountManagementService
) {
    suspend fun logout() {
        // TODO: error handling
        try {
            network.logout()
            repo.delete()
        } catch (e: HttpException) {

        }
    }
}