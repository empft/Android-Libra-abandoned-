package com.example.libraandroid.domain.finance.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceWalletDao {
    @Query("SELECT * FROM DeviceWallet")
    fun wallets(): Flow<List<DeviceWalletDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg deviceWalletDto: DeviceWalletDto)

    @Update
    fun update(vararg deviceWalletDto: DeviceWalletDto)
}