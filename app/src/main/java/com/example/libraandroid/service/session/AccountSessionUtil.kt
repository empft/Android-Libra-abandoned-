package com.example.libraandroid.service.session

import com.example.libraandroid.service.network.NetworkConstant
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