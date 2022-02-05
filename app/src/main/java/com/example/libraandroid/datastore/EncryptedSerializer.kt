package com.example.libraandroid.datastore

import androidx.datastore.core.Serializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

private class EncryptedSerializer<T>(
    private val serializer: Serializer<T>,
    private val cipher: DataStoreCipher,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): Serializer<T> {
    override val defaultValue: T
        get() = serializer.defaultValue

    override suspend fun readFrom(input: InputStream): T {
        return serializer.readFrom(
            ByteArrayInputStream(cipher.decrypt(input.readBytes()))
        )
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        val serializedStream = ByteArrayOutputStream()
        serializer.writeTo(t, serializedStream)

        withContext(dispatcher) {
            // hopefully false positive
            output.write(cipher.encrypt(serializedStream.toByteArray()))
        }
    }
}

fun <T> Serializer<T>.withEncryption(cipher: DataStoreCipher): Serializer<T> {
    return EncryptedSerializer(
        this,
        cipher
    )
}


