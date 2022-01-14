package com.example.libraandroid.ui.security

import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

// Only works in fragment activity
@Composable
fun defaultBiometricPrompt(callback: BiometricPrompt.AuthenticationCallback): BiometricPrompt {
    val activity = LocalContext.current as FragmentActivity
    val executor = ContextCompat.getMainExecutor(activity)
    return BiometricPrompt(activity, executor, callback)
}