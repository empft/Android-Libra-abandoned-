package com.example.libraandroid.domain.finance.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.libraandroid.domain.finance.chain.Chain
import com.example.libraandroid.domain.finance.chain.ChainName
import com.example.libraandroid.domain.finance.wallet.Wallet

@Entity(
    tableName = "DeviceWallet",
    primaryKeys = ["Address", "ChainName", "ChainId"],
    foreignKeys = [ForeignKey(
        entity = WalletDto::class,
        parentColumns = ["Address", "ChainName", "ChainId"],
        childColumns = ["Address", "ChainName", "ChainId"]
    )]
)
data class DeviceWalletDto(
    @ColumnInfo(name = "Address") val address: String,
    @ColumnInfo(name = "ChainName") val chainName: ChainName,
    @ColumnInfo(name = "ChainId") val chainId: Int,
    @ColumnInfo(name = "DisplayName") val displayName: String,
    @ColumnInfo(name = "HasKey") val hasKey: Boolean
) {
    fun toWallet(): Wallet {
        return when(this.chainName) {
            ChainName.Celo -> {
                Wallet.Celo(
                    address = this.address,
                    chain = Chain.Celo(
                        id = this.chainId
                    )
                )
            }
            ChainName.Diem -> {
                Wallet.Diem(
                    address = this.address,
                    chain = Chain.Diem(
                        id = this.chainId
                    )
                )
            }
        }
    }
}