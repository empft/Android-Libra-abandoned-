package com.example.libraandroid.ui.register

import androidx.annotation.StringRes

sealed interface RegisterFailure {
    object Form: RegisterFailure
    data class Id(@StringRes val id: Int): RegisterFailure
}