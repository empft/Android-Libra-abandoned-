package com.example.libraandroid.domain.finance

import java.math.BigInteger

sealed interface Amount {
    data class Celo(
        val amount: BigInteger
    ): Amount

    data class Diem(
        val amount: BigInteger
    ): Amount
}