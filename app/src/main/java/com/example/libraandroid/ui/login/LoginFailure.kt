package com.example.libraandroid.ui.login

import androidx.annotation.StringRes

sealed interface LoginFailure {
    data class Id(@StringRes val id: Int): LoginFailure
    data class Text(val message: String): LoginFailure
}
