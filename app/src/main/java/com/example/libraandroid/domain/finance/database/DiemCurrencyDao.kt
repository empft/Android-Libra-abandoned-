package com.example.libraandroid.domain.finance.database

import androidx.room.*
import com.example.libraandroid.domain.finance.chain.ChainMainNet
import com.example.libraandroid.domain.finance.chain.ChainName
import kotlinx.coroutines.flow.Flow

@Dao
interface DiemCurrencyDao {
    @Query(
        "SELECT * FROM DiemCurrency"
    )
    fun currencies(): Flow<List<DiemCurrencyDto>>

    @Query(
        "SELECT * FROM DiemCurrency " +
                "WHERE DiemCurrency.ChainId = :chainId " +
                "AND DiemCurrency.ChainName = :chainName"
    )
    fun currenciesInChain(chainId: Int, chainName: String = ChainName.Diem.name): Flow<List<DiemCurrencyDto>>
    fun currenciesInMainNet() = currenciesInChain(ChainMainNet.Diem.id)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg diemCurrency: DiemCurrencyDto)

    @Update
    fun update(vararg diemCurrency: DiemCurrencyDto)
}