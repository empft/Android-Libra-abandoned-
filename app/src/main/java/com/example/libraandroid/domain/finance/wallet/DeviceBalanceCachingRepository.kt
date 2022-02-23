package com.example.libraandroid.domain.finance.wallet

import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.chain.ChainClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.last

class DeviceBalanceCachingRepository(
    private val currencyRepository: DeviceCurrencyRepository,
    private val balanceRepository: DeviceBalanceLocalRepository,
    private val walletRepository: DeviceWalletRepository,
    private val chainClientFactory: ChainClient.Factory
) {

    fun focusedBalances(): Flow<List<Balance>> {


        walletRepository.wallets().combine(currencyRepository.focusedCurrencies()) { wallets, currencies ->
            wallets.forEach { wallet ->
                when(wallet) {
                    is Wallet.Celo -> {

                    }
                    is Wallet.Diem -> {}
                }
            }
            currencies.forEach { currency ->
                when(currency) {
                    is Currency.Celo -> {

                    }
                    is Currency.Diem -> TODO()
                }
            }
        }


        celoChainClient.balances()

        return balanceRepository.focusedBalances()
    }

}