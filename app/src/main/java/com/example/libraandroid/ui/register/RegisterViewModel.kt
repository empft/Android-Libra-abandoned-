package com.example.libraandroid.ui.register

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {
    var invitationEmailError = mutableStateOf<String?>(null)
        private set
    var invitationCodeError = mutableStateOf<String?>(null)
        private set
    var usernameError = mutableStateOf<String?>(null)
        private set
    var displayNameError = mutableStateOf<String?>(null)
        private set
    var passwordError = mutableStateOf<String?>(null)
        private set
    var emailError = mutableStateOf<String?>(null)
        private set

    var countdown = mutableStateOf<String?>(null)
        private set

    fun submitFormWithInvitation(form: RegistrationFormWithInvitation) {

    }

    fun submitForm(form: RegistrationForm) {

    }

    fun requestInvitationCode(email: String) {

    }

    fun checkNamesDifferent(username: String, displayName: String): Boolean {
        return username != displayName
    }

    fun checkSecurePassword(context: Context, password: String): Boolean {
        if (password.length < 8) {
            return false
        } else if (matchCommonCaseInsensitive(context, password)) {
            return false
        }
        return true
    }

    private fun matchCommonCaseInsensitive(context: Context, password: String): Boolean {
        context.assets.open("commonpassword.txt").bufferedReader().useLines {
            it.forEach { line ->
                if (line.equals(password, ignoreCase = true)) {
                    return true
                }
            }
        }
        return false
    }
}