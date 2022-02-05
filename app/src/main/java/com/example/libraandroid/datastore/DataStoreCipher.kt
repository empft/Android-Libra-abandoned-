package com.example.libraandroid.datastore

import com.example.libraandroid.keystore.AESKeyStore
import com.example.libraandroid.keystore.KeyStoreProvider
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec

interface DataStoreCipher {
    fun encrypt(value: ByteArray): ByteArray
    fun decrypt(value: ByteArray): ByteArray
}

class DefaultDataStoreCipher(
    private val keyAlias: String,
    provider: String = KeyStoreProvider.AndroidKeyStore
): DataStoreCipher {
    private val cipher by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
    }

    private val keyStore = AESKeyStore(provider)

    override fun encrypt(value: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, keyStore.key(keyAlias))
        return cipher.iv + cipher.doFinal(value)
    }

    override fun decrypt(value: ByteArray): ByteArray {
        val iv = value.copyOfRange(0, 12)
        val encrypted = value.copyOfRange(12, value.count())

        cipher.init(Cipher.DECRYPT_MODE, keyStore.key(keyAlias), GCMParameterSpec(128, iv))
        return cipher.doFinal(encrypted)
    }
}