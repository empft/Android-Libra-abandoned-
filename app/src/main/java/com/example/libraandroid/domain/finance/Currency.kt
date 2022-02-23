package com.example.libraandroid.domain.finance

import com.example.libraandroid.domain.finance.chain.Chain
import com.example.libraandroid.ui.currency.CurrencyConstant
import java.math.BigInteger

sealed interface Currency {
    data class Celo(
        override val code: String,
        override val decimalPlaces: Int,
        override val chain: Chain.Celo,
        val tokenAddress: String,
        val tokenName: String
    ): Currency

    data class Diem(
        override val code: String,
        override val decimalPlaces: Int = CurrencyConstant.DiemDecimal,
        override val chain: Chain.Diem
    ): Currency

    val code: String
    val decimalPlaces: Int
    val chain: Chain
}

data class FocusableCurrency(
    val currency: Currency,
    var isFocused: Boolean
)