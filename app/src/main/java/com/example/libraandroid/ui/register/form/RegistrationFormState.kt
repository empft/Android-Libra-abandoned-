package com.example.libraandroid.ui.register.form

data class RegistrationFormState(
    val invitationEmailError: Int? = null,
    val invitationCodeError: Int? = null,
    val usernameError: Int? = null,
    val displayNameError: Int? = null,
    val passwordError: Int? = null,
    val emailError: Int? = null
)
