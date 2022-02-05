package com.example.libraandroid.ui.login

import androidx.annotation.StringRes

sealed class LoginFailure {
    data class Id(@StringRes val id: Int): LoginFailure()
    data class Text(val message: String): LoginFailure()
}
