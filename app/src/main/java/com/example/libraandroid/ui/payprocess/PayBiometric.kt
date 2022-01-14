package com.example.libraandroid.ui.payprocess

import android.content.Intent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.libraandroid.ui.security.defaultBiometricPrompt

@Composable
fun PayBiometric() {
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric login for my app")
        .setSubtitle("Log in using your biometric credential")
        // Can't call setNegativeButtonText() and
        // setAllowedAuthenticators(... or DEVICE_CREDENTIAL) at the same time.
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()

    val biometricManager = BiometricManager.from(LocalContext.current)
    when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS -> {

        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {

        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {

        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            // Prompts the user to create credentials that your app accepts.

        }
        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            TODO()
        }
        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            TODO()
        }
        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            TODO()
        }
    }

    val biometricPrompt = defaultBiometricPrompt(object: BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
        }
    })
}