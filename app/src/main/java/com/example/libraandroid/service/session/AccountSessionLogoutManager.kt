package com.example.libraandroid.service.session

import com.example.libraandroid.service.network.AccountManagementService

class AccountSessionLogoutManager(
    private val repo: AccountSessionRepository,
    private val network: AccountManagementService
) {
    suspend fun logout() {
        // TODO: error handling
        network.logout()
        repo.delete()
    }
}