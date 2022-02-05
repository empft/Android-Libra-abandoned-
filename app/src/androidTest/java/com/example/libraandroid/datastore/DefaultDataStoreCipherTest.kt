package com.example.libraandroid.datastore

import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.Assert.assertEquals

internal class DefaultDataStoreCipherTest {
    @Test
    fun encryptAndDecrypt() {
        val cipher = DefaultDataStoreCipher("testKey")
        val testByte = "uwu".toByteArray()
        val encrypted = cipher.encrypt(testByte)
        val decrypted = cipher.decrypt(encrypted)

        assertEquals("Encryption or decryption failed", testByte, decrypted)
    }
}