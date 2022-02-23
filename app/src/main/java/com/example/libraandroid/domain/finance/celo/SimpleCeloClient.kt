package com.example.libraandroid.domain.finance.celo

import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.chain.ChainClient
import kotlinx.coroutines.future.await
import org.celo.contractkit.ContractKit
import org.celo.contractkit.wrapper.GoldTokenWrapper
import org.celo.contractkit.wrapper.StableTokenEURWrapper
import org.celo.contractkit.wrapper.StableTokenWrapper
import java.math.BigInteger

class SimpleCeloClient(
    private val kit: ContractKit
): ChainClient.Celo {
    /**
     * Returns map of contract address to amount
     */
    override fun balances(address: String): Map<String, BigInteger> {
        val balances = kit.getTotalBalance(address)
        return HashMap<String, BigInteger>().apply {
            put(kit.contracts.goldToken.contractAddress.removePrefix("0x"), balances.CELO)
            put(kit.contracts.stableToken.contractAddress.removePrefix("0x"), balances.cUSD)
            put(kit.contracts.stableTokenEUR.contractAddress.removePrefix("0x"), balances.cEUR)
        }
    }

    override suspend fun currency(celoCurrency: CeloCurrency): Erc20 {
        return when(celoCurrency) {
            CeloCurrency.Celo -> {
                kit.contracts.goldToken.toErc20()
            }
            CeloCurrency.CUsd -> {
                kit.contracts.stableToken.toErc20()
            }
            CeloCurrency.CEur -> {
                kit.contracts.stableTokenEUR.toErc20()
            }
        }
    }
}

private suspend fun GoldTokenWrapper.toErc20(): Erc20 {
    return Erc20(
        totalSupply = totalSupply().sendAsync().await(),
        name = name().sendAsync().await(),
        symbol = symbol().sendAsync().await(),
        decimals = decimals().sendAsync().await().toInt(),
        contractAddress = contractAddress
    )
}

private suspend fun StableTokenWrapper.toErc20(): Erc20 {
    return Erc20(
        totalSupply = this.totalSupply().sendAsync().await(),
        name = this.name().sendAsync().await(),
        symbol = this.symbol().sendAsync().await(),
        decimals = this.decimals().sendAsync().await().toInt(),
        contractAddress = this.contractAddress
    )
}

private suspend fun StableTokenEURWrapper.toErc20(): Erc20 {
    return Erc20(
        totalSupply = this.totalSupply().sendAsync().await(),
        name = this.name().sendAsync().await(),
        symbol = this.symbol().sendAsync().await(),
        decimals = this.decimals().sendAsync().await().toInt(),
        contractAddress = this.contractAddress
    )
}