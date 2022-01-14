package com.example.libraandroid.domain.session

import com.example.libraandroid.network.NetworkConstant
import retrofit2.Response
import java.net.HttpCookie

object AccountSessionUtil {
    fun saveSessionFromResponse(name: String, response: Response<*>, repo: AccountSessionRepository) {
        val rawCookie = response.headers().values("Set-Cookie").firstOrNull {
            it.startsWith(NetworkConstant.AccountSessionCookie + "=")
        }

        rawCookie?.let {
            val token = SessionToken(name = name, token = HttpCookie.parse(it).first().value)
            repo.set(token)
        } ?: throw Exception("Session cookie not found")
    }
}