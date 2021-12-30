package com.example.libraandroid.ui.wallet

sealed interface Chain {
    data class Celo(
        val id: Int,
        override val chainContext: ChainContext? = null
    ): Chain
    data class Diem(
        val id: Int,
        override val chainContext: ChainContext? = null
    ): Chain

    val chainContext: ChainContext?
}

data class ChainContext(
    val name: String
)