package com.example.libraandroid.domain.account.session

import com.example.libraandroid.network.HttpConstant
import okhttp3.Cookie
import retrofit2.Response
import java.net.HttpCookie

object AccountSessionUtil {
    fun parseToken(response: Response<*>): String {
        val rawCookie = response.headers().values(HttpConstant.SetCookie).firstOrNull {
            it.startsWith(AccountSessionConstant.CookieName + "=")
        }
        rawCookie?.let {
            return HttpCookie.parse(it).first().value
        } ?: throw Exception("Session cookie not found")
    }

    fun tokenToCookie(token: String): Cookie {
        return Cookie.Builder()
            .name(AccountSessionConstant.CookieName)
            .value(token)
            .httpOnly()
            .secure()
            .build()
    }
}