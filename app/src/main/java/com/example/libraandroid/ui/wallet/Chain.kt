package com.example.libraandroid.ui.wallet

sealed interface Chain {
    data class Celo(
        override val id: Int,
        override val chainContext: ChainContext? = null
    ): Chain
    data class Diem(
        override val id: Int,
        override val chainContext: ChainContext? = null
    ): Chain

    val chainContext: ChainContext?
    val id: Int
}

data class ChainContext(
    val name: String
)