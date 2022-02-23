package com.example.libraandroid.domain.finance.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.FocusableCurrency
import com.example.libraandroid.domain.finance.chain.Chain
import com.example.libraandroid.domain.finance.chain.ChainName

@Entity(
    tableName = "CeloCurrency",
    primaryKeys = ["TokenAddress", "ChainName", "ChainId"],
    foreignKeys = [ForeignKey(
        entity = ChainDto::class,
        parentColumns = ["Name", "Id"],
        childColumns = ["ChainName", "ChainId"]
    )]
)
data class CeloCurrencyDto(
    @ColumnInfo(name = "TokenAddress")  val tokenAddress: String,
    @ColumnInfo(name = "ChainName") val chainName: ChainName,
    @ColumnInfo(name = "ChainId") val chainId: Int,
    @ColumnInfo(name = "Currency") val currency: String,
    @ColumnInfo(name = "DecimalPlaces") val decimalPlaces: Int,
    @ColumnInfo(name = "TokenName")  val tokenName: String,
    @ColumnInfo(name = "IsFocused") val isFocused: Boolean
) {
    fun toCurrency(): Currency {
        return Currency.Celo(
            code = this.currency,
            decimalPlaces = this.decimalPlaces,
            chain = Chain.Celo(
                id = this.chainId
            ),
            tokenAddress = this.tokenAddress,
            tokenName = this.tokenName
        )
    }

    fun toFocusedCurrency(): FocusableCurrency {
        return FocusableCurrency(
            currency = this.toCurrency(),
            isFocused = this.isFocused
        )
    }
}