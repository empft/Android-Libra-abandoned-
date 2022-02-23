package com.example.libraandroid.domain.finance.wallet

import com.example.libraandroid.domain.finance.FocusableCurrency
import com.example.libraandroid.domain.finance.wallet.DeviceCurrencyConst.MaxFocusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull

class DeviceBalanceInteractor(
    private val deviceCurrencyRepository: DeviceCurrencyRepository
) {
    val focusableCurrencies: Flow<List<FocusableCurrency>> = deviceCurrencyRepository.focusableCurrencies()

    /**
     *
     */
    suspend fun toggleFocus(index: Int) {
        var count = 0
        val currencies = focusableCurrencies.last()
        currencies[index].isFocused = !currencies[index].isFocused

        for (currency in currencies) {
            if (currency.isFocused) {
                count += 1
            }
        }

        if (count < MaxFocusable) {
            deviceCurrencyRepository
        } else {

        }
    }
}