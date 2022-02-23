package com.example.libraandroid.domain.finance.database

import androidx.room.*
import com.example.libraandroid.domain.finance.chain.ChainName
import java.math.BigInteger

@Entity(
    tableName = "Chain",
    primaryKeys = ["Name", "Id"],
    indices = [Index(value = ["DisplayName"], unique = true)]
)
data class ChainDto(
    @ColumnInfo(name = "Name") val name: ChainName,
    @ColumnInfo(name = "Id") val id: Int,
    @ColumnInfo(name = "DisplayName") val displayName: String,
)

@Entity(
    tableName = "ChainEndpoint",
    primaryKeys = ["Name", "Id"],
    foreignKeys = [ForeignKey(
        entity = ChainDto::class,
        parentColumns = ["Name", "Id"],
        childColumns = ["Name", "Id"]
    )]
)
data class ChainEndpointDto(
    @ColumnInfo(name = "Name") val name: ChainName,
    @ColumnInfo(name = "Id") val id: Int,
    @ColumnInfo(name = "Url") val url: String,
)