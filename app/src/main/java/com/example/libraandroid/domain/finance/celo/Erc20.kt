package com.example.libraandroid.domain.finance.celo

import java.math.BigInteger

data class Erc20(
    val totalSupply: BigInteger,
    val name: String,
    val symbol: String,
    val decimals: Int,
    val contractAddress: String
)
