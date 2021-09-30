package com.example.libraandroid.service.session

import com.example.libraandroid.R
import com.example.libraandroid.service.network.*
import com.example.libraandroid.service.network.ResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.net.HttpCookie

class AccountSessionLoginManager(
    private val repo: AccountSessionRepository,
    private val network: AccountLoginService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun login(username: String, password: String) {
        val response = network.login(username, password)
        if (response.isSuccessful) {
            val rawCookie = response.headers().values("Set-Cookie").firstOrNull {
                it.startsWith(NetworkConstant.ACCOUNT_SESSION_COOKIE + "=")
            }

            rawCookie?.let {
                val token = SessionToken(name = username, token = HttpCookie.parse(it).first().value)
                repo.set(token)
            } ?: throw Exception("Session cookie not found")
        } else {
            response.errorBody()?.let {
                val text = it.charStream().readText()

                withContext(dispatcher) {
                    it.close()
                }
                throw ResponseException.from(text)
            }
            throw ResponseException(-1)
        }
    }
}