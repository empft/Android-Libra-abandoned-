package com.example.libraandroid.domain.finance.chain

sealed interface Chain {
    data class Celo(
        override val id: Int
    ): Chain

    data class Diem(
        override val id: Int
    ): Chain

    val id: Int
}