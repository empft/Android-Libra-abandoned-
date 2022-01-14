package com.example.libraandroid.domain.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionToken(
    val name: String,
    val token: String
)
