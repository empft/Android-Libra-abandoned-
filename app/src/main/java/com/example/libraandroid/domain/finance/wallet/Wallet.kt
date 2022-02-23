package com.example.libraandroid.domain.finance.wallet

import com.example.libraandroid.domain.finance.chain.Chain

sealed interface Wallet {
    data class Celo(
        override val address: String,
        override val chain: Chain.Celo
    ): Wallet

    data class Diem(
        override val address: String,
        override val chain: Chain.Diem
    ): Wallet

    val address: String
    val chain: Chain
}

