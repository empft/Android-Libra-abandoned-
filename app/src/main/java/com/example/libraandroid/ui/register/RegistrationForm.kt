package com.example.libraandroid.ui.register

data class RegistrationForm(
    var username: String = "",
    var displayName: String = "",
    var password: String = "",
    var email: String = ""
)

data class RegistrationFormWithInvitation(
    var invitationEmail: String = "",
    var invitationCode: String = "",
    var username: String = "",
    var displayName: String = "",
    var password: String = "",
    var email: String = ""
)
