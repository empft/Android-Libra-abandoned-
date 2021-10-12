package com.example.libraandroid.ui.register

sealed class PasswordResult {
    object Success: PasswordResult()
    object Loading: PasswordResult()
    object Empty: PasswordResult()
}
