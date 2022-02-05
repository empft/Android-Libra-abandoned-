package com.example.libraandroid.domain.applicationsession

class GuestLoginInteractor(
    private val sessionManager: SessionManager
) {
    suspend fun login() {
        sessionManager.initGuestSession()
    }
}