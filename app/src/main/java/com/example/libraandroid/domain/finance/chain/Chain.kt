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
enum class ChainName {
    Celo,
    Diem,
}

enum class ChainMainNet(val id: Int) {
    Celo(42220),
    Diem(1),
}
