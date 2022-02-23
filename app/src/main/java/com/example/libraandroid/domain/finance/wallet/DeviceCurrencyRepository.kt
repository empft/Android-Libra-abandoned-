package com.example.libraandroid.domain.finance.wallet

import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.FocusableCurrency
import com.example.libraandroid.domain.finance.database.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class DeviceCurrencyRepository(
    private val diemCurrencyDao: DiemCurrencyDao,
    private val celoCurrencyDao: CeloCurrencyDao,
    private val deviceCurrencyDao: DeviceCurrencyDao
) {
    fun focusedCurrencies(): Flow<List<Currency>> {

    }


    private fun zipFocusableCurrencies(
        diemCurrency: Flow<List<DiemCurrencyDto>>,
        celoCurrency: Flow<List<CeloCurrencyDto>>
    ): Flow<List<FocusableCurrency>> {
        val flow = diemCurrency
            .map { currencies ->
                currencies.map {
                    it.toFocusedCurrency()
                }
            }

        return celoCurrency.map { currencies ->
            currencies.map {
                it.toFocusedCurrency()
            }
        }.zip(flow) { i, j ->
            i + j
        }
    }

    fun focusableCurrencies(): Flow<List<FocusableCurrency>> {
        return zipFocusableCurrencies(
            diemCurrencyDao
                .currenciesInMainNet(),
            celoCurrencyDao
                .currenciesInMainNet()
        )
    }

    suspend fun setFocusableCurrencies(currencies: List<FocusableCurrency>) {
        deviceCurrencyDao.setExclusiveFocusedCurrenciesInMainNet(currencies)
    }
}