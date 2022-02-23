package com.example.libraandroid.domain.finance.chain

import com.example.libraandroid.domain.finance.celo.CeloCurrency
import com.example.libraandroid.domain.finance.celo.Erc20
import com.example.libraandroid.domain.finance.wallet.Balance
import java.math.BigInteger

sealed interface ChainClient {
    interface Celo: ChainClient {
        suspend fun currency(celoCurrency: CeloCurrency): Erc20
    }

    interface Diem: ChainClient {

    }

    fun balances(address: String): Map<String, BigInteger>

    interface Factory {
        suspend fun celoClient(chainId: Int): Celo
        suspend fun diemClient(chainId: Int): Diem
    }
}

