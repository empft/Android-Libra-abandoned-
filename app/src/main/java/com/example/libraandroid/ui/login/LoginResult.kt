package com.example.libraandroid.ui.login

import androidx.annotation.StringRes

sealed class LoginResult {
    object Success: LoginResult()
    object Empty: LoginResult()
    data class FailureId(@StringRes val id: Int): LoginResult()
    data class Failure(val message: String): LoginResult()
}
