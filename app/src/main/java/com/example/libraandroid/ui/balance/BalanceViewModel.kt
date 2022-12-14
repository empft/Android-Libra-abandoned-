package com.example.libraandroid.ui.balance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BalanceViewModel(
    private val settings: BalanceSettings
): ViewModel() {
    var balances = mutableStateListOf<Balance>()
        private set

    var focusedBalances = mutableStateListOf<Balance>()
        private set

    @Composable
    fun balanceViewMode(): State<BalanceViewMode> {
        return settings.balanceViewMode().collectAsState(initial = BalanceViewMode.Currency)
    }
    suspend fun balanceViewMode(mode: BalanceViewMode) {
        settings.balanceViewMode(mode)
    }

    suspend fun refreshFocusedBalances() {

        focusedBalances
    }
}

class BalanceViewModelFactory(private val settings: BalanceSettings): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return BalanceViewModel(settings) as T
    }
}