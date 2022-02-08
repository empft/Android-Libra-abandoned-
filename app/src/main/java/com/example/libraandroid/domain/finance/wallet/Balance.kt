package com.example.libraandroid.domain.finance.wallet

import java.math.BigInteger

sealed interface Balance {
    data class Diem(
        override val amount: BigInteger,
        override val currency: String
    ): Balance

    data class Celo(
        override val amount: BigInteger,
        override val currency: String
    ): Balance

    val amount: BigInteger
    val currency: String
}
