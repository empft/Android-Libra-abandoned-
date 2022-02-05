package com.example.libraandroid.domain.account.session

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class AccountSessionCookieJar(
    private val accountSession: AccountSession
): CookieJar {
    private var cookies = mutableListOf<Cookie>()

    init {
        cookies.add(AccountSessionUtil.tokenToCookie(accountSession.token))
    }

    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        this.cookies = cookies
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return cookies
    }
}