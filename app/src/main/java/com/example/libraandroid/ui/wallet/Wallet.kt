package com.example.libraandroid.ui.wallet

import com.example.libraandroid.ui.account.AppAccount

sealed interface Wallet {
    data class Celo(
        val chain: Chain.Celo,
        override val address: String,
        override val walletContext: WalletContext? = null,
    ): Wallet

    data class Diem(
        val chain: Chain.Diem,
        override val address: String,
        override val walletContext: WalletContext? = null,
    ): Wallet

    val address: String
    val walletContext: WalletContext?
}

data class WalletContext(
    // wallet name
    val name: String? = null,
    val appAccount: AppAccount? = null
)


