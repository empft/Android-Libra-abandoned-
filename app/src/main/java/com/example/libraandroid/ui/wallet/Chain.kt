package com.example.libraandroid.ui.wallet

sealed interface Chain {
    data class Celo(
        val id: Int,
        val chainContext: ChainContext? = null
    ): Chain
    data class Diem(
        val id: Int,
        val chainContext: ChainContext? = null
    ): Chain
}

data class ChainContext(
    val name: String
)