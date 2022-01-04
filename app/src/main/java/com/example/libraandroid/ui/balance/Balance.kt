package com.example.libraandroid.ui.balance

import com.example.libraandroid.ui.currency.CurrencyConstant
import com.example.libraandroid.ui.wallet.Wallet
import java.math.BigInteger

sealed interface Balance {
    data class Diem(
        override val amount: BigInteger,
        override val decimalPlaces: Int = CurrencyConstant.DiemDecimal,
        override val currency: String,
        override val wallet: Wallet.Diem
    ): Balance

    data class Celo(
        override val amount: BigInteger,
        override val decimalPlaces: Int,
        override val currency: String,
        override val wallet: Wallet.Celo
    ): Balance

    val wallet: Wallet
    val currency: String
    val decimalPlaces: Int
    val amount: BigInteger
}