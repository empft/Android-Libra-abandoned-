package com.example.libraandroid.domain.account.registration

data class RegistrationError(
    val username: String? = null,
    val displayName: String? = null,
    val password: String? = null,
    val email: String? = null,
    val invitationEmail: String? = null,
    val invitationCode: String? = null
)