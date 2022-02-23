package com.example.libraandroid.ui.register

sealed interface PasswordResult {
    object Success: PasswordResult
    object Loading: PasswordResult
    object Empty: PasswordResult
}
