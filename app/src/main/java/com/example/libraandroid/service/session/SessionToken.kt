package com.example.libraandroid.service.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionToken(
    val name: String,
    val token: String
)
