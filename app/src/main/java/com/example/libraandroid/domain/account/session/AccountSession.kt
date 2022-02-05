package com.example.libraandroid.domain.account.session

import com.example.libraandroid.domain.applicationsession.ApplicationSession

data class AccountSession(
    val token: String
) {
    fun from(applicationSession: ApplicationSession.Type.User): AccountSession {
        return AccountSession(applicationSession.savedAccountSession.token)
    }
}
