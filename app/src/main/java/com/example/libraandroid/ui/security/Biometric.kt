package com.example.libraandroid.ui.security

import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt

private fun Bio() {
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric login for my app")
        .setSubtitle("Log in using your biometric credential")
        // Can't call setNegativeButtonText() and
        // setAllowedAuthenticators(... or DEVICE_CREDENTIAL) at the same time.
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()

}