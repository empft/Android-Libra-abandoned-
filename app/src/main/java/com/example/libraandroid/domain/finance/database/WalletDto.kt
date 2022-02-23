package com.example.libraandroid.domain.finance.database

import androidx.room.*
import com.example.libraandroid.domain.finance.chain.ChainName
import java.math.BigInteger

@Entity(
    tableName = "Wallet",
    primaryKeys = ["Address", "ChainName", "ChainId"],
    foreignKeys = [ForeignKey(
        entity = ChainDto::class,
        parentColumns = ["Name", "Id"],
        childColumns = ["ChainName", "ChainId"]
    )]
)
data class WalletDto(
    @ColumnInfo(name = "Address") val address: String,
    @ColumnInfo(name = "ChainName") val chainName: ChainName,
    @ColumnInfo(name = "ChainId") val chainId: Int
)