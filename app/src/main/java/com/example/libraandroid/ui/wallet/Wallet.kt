package com.example.libraandroid.ui.wallet

import com.example.libraandroid.ui.account.AppAccount

sealed interface Wallet {
    data class Celo(
        val address: String,
        val chain: Chain,
        val walletContext: WalletContext? = null
    ): Wallet, WalletWithAccount {
        override fun address() = address
        override fun account() = walletContext?.appAccount
    }

    data class Diem(
        val address: String,
        val chain: Chain,
        val walletContext: WalletContext? = null
    ): Wallet, WalletWithAccount {
        override fun address() = address
        override fun account() = walletContext?.appAccount
    }
}

data class WalletContext(
    // wallet name
    val name: String? = null,
    val appAccount: AppAccount? = null
)

interface WalletWithAccount {
    fun address(): String
    fun account(): AppAccount?
}


