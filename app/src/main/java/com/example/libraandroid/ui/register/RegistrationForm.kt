package com.example.libraandroid.ui.register

data class RegistrationForm(
    val username: String,
    val displayName: String,
    val password: String,
    val email: String
)

data class RegistrationFormWithInvitation(
    val invitationEmail: String,
    val invitationCode: String,
    val username: String,
    val displayName: String,
    val password: String,
    val email: String
)
