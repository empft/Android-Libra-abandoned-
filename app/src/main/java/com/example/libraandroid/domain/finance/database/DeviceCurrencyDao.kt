package com.example.libraandroid.domain.finance.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.FocusableCurrency
import com.example.libraandroid.domain.finance.chain.ChainMainNet
import com.example.libraandroid.domain.finance.chain.ChainName

@Dao
interface DeviceCurrencyDao {
    @Query(
        "UPDATE DiemCurrency SET IsFocused = " +
                "CASE " +
                "WHEN Currency IN (:currencies) THEN :onTrue " +
                "ELSE :onFalse " +
                "END " +
                "WHERE ChainName = :chainName AND ChainId = :chainId"
    )
    suspend fun setDiemExclusiveFocusedCurrenciesInMainNet(
        currencies: List<String>,
        chainId: Int = ChainMainNet.Diem.id,
        chainName: String = ChainName.Diem.name,
        onTrue: Boolean = true, onFalse: Boolean = false
    )

    @Query(
        "UPDATE CeloCurrency SET IsFocused = " +
                "CASE " +
                "WHEN Currency IN (:currencies) THEN :onTrue " +
                "ELSE :onFalse " +
                "END " +
                "WHERE ChainName = :chainName AND ChainId = :chainId"
    )
    suspend fun setCeloExclusiveFocusedCurrenciesInMainNet(
        currencies: List<String>,
        chainId: Int = ChainMainNet.Celo.id,
        chainName: String = ChainName.Celo.name,
        onTrue: Boolean = true, onFalse: Boolean = false
    )

    @Transaction
    suspend fun setExclusiveFocusedCurrenciesInMainNet(focusable: List<FocusableCurrency>) {
        val diem = mutableListOf<String>()
        val celo = mutableListOf<String>()

        focusable.forEach {
            if (it.isFocused) {
                when(val currency = it.currency) {
                    is Currency.Celo -> {
                        if (currency.chain.id == ChainMainNet.Celo.id) {
                            celo.add(currency.tokenAddress)
                        }
                    }
                    is Currency.Diem -> {
                        if (currency.chain.id == ChainMainNet.Diem.id) {
                            diem.add(currency.code)
                        }
                    }
                }
            }
        }

        setDiemExclusiveFocusedCurrenciesInMainNet(diem)
        setCeloExclusiveFocusedCurrenciesInMainNet(celo)
    }
}