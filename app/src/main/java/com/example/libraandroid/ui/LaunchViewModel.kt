package com.example.libraandroid.ui

import androidx.lifecycle.ViewModel
import com.example.libraandroid.domain.applicationsession.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LaunchViewModel(
    sessionManager: SessionManager
): ViewModel() {
    val isLoggedIn: Flow<LaunchState> = sessionManager.currentSession.map {
        if (it != null) {
            LaunchState.LoggedIn
        } else {
            LaunchState.LoggedOut
        }
    }
}

enum class LaunchState {
    Loading,
    LoggedIn,
    LoggedOut
}