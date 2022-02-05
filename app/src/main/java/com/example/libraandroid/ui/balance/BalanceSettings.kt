package com.example.libraandroid.ui.balance

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.libraandroid.datastore.PrefDataStoreConst
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PrefDataStoreConst.BalanceUi)

enum class BalanceViewMode {
    Currency,
    Wallet
}

class BalanceSettings(
    private val context: Context
) {
    enum class Field {
        BalanceViewType
    }

    fun balanceViewMode(): Flow<BalanceViewMode> {
        val key = stringPreferencesKey(Field.BalanceViewType.toString())

        return context.dataStore.data.map {
            BalanceViewMode.valueOf(it[key] ?: BalanceViewMode.Currency.name)
        }
    }

    suspend fun balanceViewMode(value: BalanceViewMode) {
        val key = stringPreferencesKey(Field.BalanceViewType.toString())

        context.dataStore.edit {
            it[key] = value.name
        }
    }

}