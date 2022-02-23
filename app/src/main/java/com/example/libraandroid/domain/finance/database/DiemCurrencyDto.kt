package com.example.libraandroid.domain.finance.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.FocusableCurrency
import com.example.libraandroid.domain.finance.chain.Chain
import com.example.libraandroid.domain.finance.chain.ChainName

@Entity(
    tableName = "DiemCurrency",
    primaryKeys = ["ChainName", "ChainId", "Currency"]
)
data class DiemCurrencyDto(
    @ColumnInfo(name = "Currency") val currency: String,
    @ColumnInfo(name = "ChainName") val chainName: ChainName,
    @ColumnInfo(name = "ChainId") val chainId: Int,
    @ColumnInfo(name = "IsFocused") val isFocused: Boolean
) {
    fun toCurrency(): Currency {
        return Currency.Diem(
            code = currency,
            chain = Chain.Diem(
                id = chainId
            )
        )
    }

    fun toFocusedCurrency(): FocusableCurrency {
        return FocusableCurrency(
            toCurrency(),
            isFocused = isFocused
        )
    }
}