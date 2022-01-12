package com.example.libraandroid.ui.balance

import com.example.libraandroid.ui.currency.Currency
import com.example.libraandroid.ui.wallet.Wallet
import java.math.BigInteger

sealed interface Balance {
    data class Diem(
        override val amount: BigInteger,
        override val currency: Currency.Diem,
        override val wallet: Wallet.Diem
    ): Balance

    data class Celo(
        override val amount: BigInteger,
        override val currency: Currency.Celo,
        override val wallet: Wallet.Celo
    ): Balance

    val wallet: Wallet
    val currency: Currency
    val amount: BigInteger
}