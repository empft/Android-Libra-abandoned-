package com.example.libraandroid.domain.applicationsession

import com.example.libraandroid.domain.account.login.LoginError
import com.example.libraandroid.domain.account.login.LoginInteractor
import com.example.libraandroid.miscellaneous.Either

class ApplicationLoginInteractor(
    private val sessionManager: SessionManager,
    private val accountLoginInteractor: LoginInteractor
) {
    suspend fun login(username: String, password: String): Either<Unit, LoginError> {
        return when(val token = accountLoginInteractor.login(username, password)) {
            is Either.Failure -> {
                Either.Failure(token.value)
            }
            is Either.Success -> {
                sessionManager.initSession(
                    ApplicationSession.Type.User(
                        savedAccountSession = ApplicationSession.Type.User.SavedAccountSession(
                            token = token.value,
                            name = username
                        )
                    )
                )
                Either.Success(Unit)
            }
        }
    }

    suspend fun guestLogin() {
        sessionManager.initGuestSession()
    }
}