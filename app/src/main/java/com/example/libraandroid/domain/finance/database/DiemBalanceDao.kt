package com.example.libraandroid.domain.finance.database

import androidx.room.*
import com.example.libraandroid.domain.finance.chain.ChainMainNet
import com.example.libraandroid.domain.finance.chain.ChainName
import kotlinx.coroutines.flow.Flow

@Dao
interface DiemBalanceDao {
    @Query(
        "SELECT * FROM DiemBalance " +
                "WHERE EXISTS (SELECT 1 FROM DeviceWallet, DiemCurrency WHERE " +
                "DiemBalance.WalletAddress = DeviceWallet.Address AND " +
                "DiemBalance.ChainId = DeviceWallet.ChainName AND " +
                "DiemBalance.ChainName = DeviceWallet.ChainName AND " +
                "DiemBalance.Currency = DiemCurrency.Currency AND " +
                "DiemBalance.ChainName = DiemCurrency.ChainName AND " +
                "DiemBalance.ChainId = DiemCurrency.ChainId AND " +
                "DiemCurrency.IsFocused = :isFocused)"
    )
    fun focusedBalancesInDeviceWallets(isFocused: Boolean = true): Flow<List<DiemBalanceDto>>

    @Query(
        "SELECT * FROM DiemBalance " +
                "WHERE EXISTS (SELECT 1 FROM DeviceWallet WHERE " +
                "DiemBalance.WalletAddress = DeviceWallet.Address AND " +
                "DiemBalance.ChainId = DeviceWallet.ChainName AND " +
                "DiemBalance.ChainName = DeviceWallet.ChainName)"
    )
    fun balancesInDeviceWallets(): Flow<List<DiemBalanceDto>>

    @Query(
        "SELECT * FROM DiemBalance " +
                "WHERE DiemBalance.WalletAddress = :address AND " +
                "DiemBalance.ChainId = :chainId AND " +
                "DiemBalance.ChainName = :chainName"
    )
    fun balances(address: String, chainId: Int, chainName: String = ChainName.Diem.name): Flow<List<DiemBalanceDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg diemBalance: DiemBalanceDto)

    @Update
    fun update(vararg diemBalance: DiemBalanceDto)
}