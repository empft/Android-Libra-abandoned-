package com.example.libraandroid.domain.finance.database

import androidx.room.*
import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.FocusableCurrency
import com.example.libraandroid.domain.finance.chain.Chain
import com.example.libraandroid.domain.finance.chain.ChainName
import com.example.libraandroid.domain.finance.wallet.Balance
import com.example.libraandroid.domain.finance.wallet.Wallet
import java.math.BigInteger

@Entity(
    tableName = "CeloBalance",
    primaryKeys = ["WalletAddress", "ChainName", "ChainId", "CurrencyAddress"],
    foreignKeys = [ForeignKey(
        entity = CeloCurrencyDto::class,
        parentColumns = ["TokenAddress", "ChainName", "ChainId"],
        childColumns = ["CurrencyAddress", "ChainName", "ChainId"]
    ), ForeignKey(
        entity = WalletDto::class,
        parentColumns = ["Address", "ChainName", "ChainId"],
        childColumns = ["WalletAddress", "ChainName", "ChainId"],
    )])
data class CeloBalanceDto(
    @ColumnInfo(name = "WalletAddress") val walletAddress: String,
    @ColumnInfo(name = "ChainName") val chainName: ChainName,
    @ColumnInfo(name = "ChainId") val chainId: Int,
    @ColumnInfo(name = "Amount") val amount: BigInteger,
    @ColumnInfo(name = "CurrencyAddress") val currencyAddress: String
) {
    fun toBalance(currency: CeloCurrencyDto): Balance {

        return Balance(
            amount = this.amount,
            currency = currency.toCurrency(),
            wallet = Wallet.Celo(
                address = this.walletAddress,
                chain = Chain.Celo(
                    id = this.chainId
                )
            )
        )
    }
}