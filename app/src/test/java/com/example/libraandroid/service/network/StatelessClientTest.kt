package com.example.libraandroid.service.network

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.jupiter.api.Assertions
import retrofit2.Response

internal class StatelessClientTest {

    @Test
    fun login() {
        val credentialError = NamedError(code = 400, message = "Invalid username or password")
        val unnamedError = "Unexpected error"

        val mockSuccess = Response.success(Unit)
        val mockFailure = Response.error<String>(400,
            ResponseBody.create(
                MediaType.parse("application/json"),
                Json.encodeToJsonElement(credentialError).toString()
            )
        )
        val mockUnnamedFailure = Response.error<String>(500,
            ResponseBody.create(
                MediaType.parse("application/json"),
                Json.encodeToJsonElement(unnamedError).toString()
            )
        )

        Assertions.assertEquals(200, mockSuccess.code(), "Invalid success response code")
        Assertions.assertEquals(Unit, mockSuccess.body(), "Cannot parse empty response body")
        Assertions.assertEquals(400, mockFailure.code(), "Invalid 400 failure response code")
        Assertions.assertEquals(credentialError, Json.decodeFromJsonElement<NamedError>(Json.parseToJsonElement(mockFailure.errorBody()!!.string())))
        Assertions.assertEquals(500, mockUnnamedFailure.code(), "Invalid 500 failure response code")
        Assertions.assertEquals(unnamedError, Json.decodeFromJsonElement<String>(Json.parseToJsonElement(mockUnnamedFailure.errorBody()!!.string())))
    }


}