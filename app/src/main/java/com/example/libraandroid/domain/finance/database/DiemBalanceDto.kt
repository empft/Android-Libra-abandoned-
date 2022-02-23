package com.example.libraandroid.domain.finance.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.FocusableCurrency
import com.example.libraandroid.domain.finance.chain.Chain
import com.example.libraandroid.domain.finance.chain.ChainName
import com.example.libraandroid.domain.finance.wallet.Balance
import com.example.libraandroid.domain.finance.wallet.Wallet
import java.math.BigInteger

@Entity(
    tableName = "DiemBalance",
    primaryKeys = ["WalletAddress", "ChainName", "ChainId", "Currency"],
    foreignKeys = [ForeignKey(
        entity = WalletDto::class,
        parentColumns = ["Address", "ChainName", "ChainId"],
        childColumns = ["WalletAddress", "ChainName", "ChainId"]
    ), ForeignKey(
        entity = DiemCurrencyDto::class,
        parentColumns = ["ChainName", "ChainId", "Currency"],
        childColumns = ["ChainName", "ChainId", "Currency"]
    )]
)
data class DiemBalanceDto(
    @ColumnInfo(name = "WalletAddress") val walletAddress: String,
    @ColumnInfo(name = "ChainName") val chainName: ChainName,
    @ColumnInfo(name = "ChainId") val chainId: Int,
    @ColumnInfo(name = "Currency") val currency: String,
    @ColumnInfo(name = "Amount") val amount: BigInteger
) {
    fun toBalance(): Balance {
        val diemChain = Chain.Diem(
            id = this.chainId
        )

        return Balance(
            amount = this.amount,
            currency = Currency.Diem(
                code = this.currency,
                chain = diemChain
            ),
            wallet = Wallet.Diem(
                address = this.walletAddress,
                chain = diemChain
            )
        )
    }
}