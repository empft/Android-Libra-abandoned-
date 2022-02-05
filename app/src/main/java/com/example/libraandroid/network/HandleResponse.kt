package com.example.libraandroid.network

import com.example.libraandroid.miscellaneous.Either
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import retrofit2.Response

object ResponseHandler {
    /**
     * Deserialize Json error
     *
     * @throws Exception if no error response body
     */
    fun <R, T> forgeError(errorDeserializer: KSerializer<T>, response: Response<R>): Either<Response<R>, T> {
        if (response.isSuccessful) {
            return Either.Success(response)
        } else {
            response.errorBody()?.let { it ->
                val text = it.charStream().use { reader ->
                    reader.readText()
                }
                return Either.Failure(Json.decodeFromString(errorDeserializer, text))
            }
            throw Exception("Expected response error body")
        }
    }

    /**
     * Deserialize Json error and response
     *
     * @throws Exception if no response body
     */
    fun <R, T> forge(errorDeserializer: KSerializer<T>, response: Response<R>): Either<R, T> {
        return when(val result = this.forgeError(errorDeserializer, response)) {
            is Either.Failure -> {
                Either.Failure(result.value)
            }
            is Either.Success -> {
                val content = response.body()
                if (content != null) {
                    Either.Success(content)
                } else {
                    throw Exception("Expected response body")
                }
            }
        }
    }
}

