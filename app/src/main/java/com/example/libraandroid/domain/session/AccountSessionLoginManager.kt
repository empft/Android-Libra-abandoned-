package com.example.libraandroid.domain.session

import com.example.libraandroid.domain.account.AccountLoginService
import com.example.libraandroid.network.ResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountSessionLoginManager(
    private val repo: AccountSessionRepository,
    private val network: AccountLoginService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    /**
     * Send a network request to login and save the session cookie
     *
     * @throws ResponseException if http code is not successful
     */
    suspend fun login(username: String, password: String) {
        val response = network.login(username, password)
        if (response.isSuccessful) {
            AccountSessionUtil.saveSessionFromResponse(username, response, repo)
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