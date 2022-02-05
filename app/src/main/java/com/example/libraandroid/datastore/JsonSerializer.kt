package com.example.libraandroid.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class JsonSerializer<T>(
    private val type: KSerializer<T>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): Serializer<T> {
    override val defaultValue: T
        get() = Json.decodeFromString(type, "")

    override suspend fun readFrom(input: InputStream): T {
        return try {
            Json.decodeFromString(type, input.readBytes().decodeToString())
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read json serialized", serialization)
        }
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        withContext(dispatcher) {
            // hopefully false positive
            output.write(Json.encodeToString(type, t).encodeToByteArray())
        }
    }
}
