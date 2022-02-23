package com.example.libraandroid.domain.finance.wallet

import com.example.libraandroid.domain.finance.FocusableCurrency
import com.example.libraandroid.domain.finance.database.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class DeviceWalletRepository(
    private val deviceWalletDao: DeviceWalletDao
) {
    fun wallets(): Flow<List<Wallet>> {
        return deviceWalletDao
            .wallets()
            .map { wallets ->
                wallets.map {
                    it.toWallet()
                }
            }
    }
}