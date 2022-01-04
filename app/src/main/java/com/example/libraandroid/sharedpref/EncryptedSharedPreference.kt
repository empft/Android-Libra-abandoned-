package com.example.libraandroid.sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

fun getEncryptedSharedPreference(context: Context): SharedPreferences {
    val sharedPrefsFile: String = EncryptedSharedPrefConst.FileSecretSharedPreferences
    return EncryptedSharedPreferences.create(
        sharedPrefsFile,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}
