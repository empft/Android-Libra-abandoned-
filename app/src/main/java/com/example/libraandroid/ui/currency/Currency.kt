package com.example.libraandroid.ui.currency

sealed interface Currency {
    data class Celo(
        val tokenAddress: String,
        val tokenName: String,
        override val decimalPlaces: Int,
        override val code: String
    ): Currency

    data class Diem(
        override val code: String,
        override val decimalPlaces: Int = CurrencyConstant.DiemDecimal
    ): Currency

    val code: String
    val decimalPlaces: Int
}