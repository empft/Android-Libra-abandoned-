package com.example.libraandroid.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * Retrieve key for encryption and decryption
 */
class AESKeyStore(
    private val provider: String
) {
    private val keyStore by lazy {
        KeyStore.getInstance(provider).apply {
            load(null)
        }
    }

    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, provider)
    }

    private fun generateSecretKey(keyAlias: String): SecretKey {
        val spec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            build()
        }

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    fun key(keyAlias: String): SecretKey {
        if (keyStore.getEntry(keyAlias, null) != null) {
            val secretKeyEntry = keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry
            return secretKeyEntry.secretKey ?: generateSecretKey(keyAlias)
        }
        return generateSecretKey(keyAlias)
    }
}