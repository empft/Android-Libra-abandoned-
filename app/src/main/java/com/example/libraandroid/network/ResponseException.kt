package com.example.libraandroid.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
data class NamedError(
    val code: Int,
    val message: String
) {
    companion object {
        fun from(encodedJson: String): NamedError {
            val error = Json.decodeFromJsonElement<NamedError>(Json.parseToJsonElement(encodedJson))
            return NamedError(error.code, error.message)
        }
    }
}

class ResponseException: Exception {
    private val _code: Int
    val code: Int
        get() = _code

    constructor(code: Int): super() {
        this._code = code
    }
    constructor( code: Int, message: String,): super(message) {
        this._code = code
    }
    constructor(code: Int, message: String, cause: Throwable): super(message, cause) {
        this._code = code
    }
    constructor(code: Int, cause: Throwable): super(cause) {
        this._code = code
    }

    companion object {
        fun from(encodedJson: String): ResponseException {
            val error = NamedError.from(encodedJson)
            return ResponseException(error.code, error.message)
        }
    }
}