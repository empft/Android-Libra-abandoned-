package com.example.libraandroid.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LaunchViewModel: ViewModel() {
    var isLoggedIn = mutableStateOf(LaunchState.Loading)
        private set

    fun logout() {
        isLoggedIn.value = LaunchState.LoggedOut
    }

    fun login() {
        isLoggedIn.value = LaunchState.LoggedIn
    }
}

enum class LaunchState {
    Loading,
    LoggedIn,
    LoggedOut
}