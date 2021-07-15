package com.example.libraandroid.ui.register.form

data class RegistrationForm(
    var invitationEmail: String = "",
    var invitationCode: String = "",
    var username: String = "",
    var displayName: String = "",
    var password: String = "",
    var email: String = ""
)
