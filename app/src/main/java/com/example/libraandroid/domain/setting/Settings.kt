package com.example.libraandroid.domain.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.libraandroid.datastore.PrefDataStoreConst
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PrefDataStoreConst.Settings)

class Settings(
    private val context: Context
) {
    enum class Field {
        RawTransaction
    }

    inner class Developer() {
        fun showRawTransaction(): Flow<Boolean> {
            val key = booleanPreferencesKey(Field.RawTransaction.toString())

            return context.dataStore.data.map {
                it[key] ?: false
            }
        }

        suspend fun showRawTransaction(value: Boolean) {
            val key = booleanPreferencesKey(Field.RawTransaction.toString())

            context.dataStore.edit {
                it[key] = value
            }
        }


    }
}