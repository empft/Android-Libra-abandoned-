package com.example.libraandroid.domain.finance.database

import androidx.room.*
import com.example.libraandroid.domain.finance.chain.ChainMainNet
import com.example.libraandroid.domain.finance.chain.ChainName
import kotlinx.coroutines.flow.Flow

@Dao
interface CeloCurrencyDao {
    @Query(
        "SELECT * FROM CeloCurrency"
    )
    fun currencies(): Flow<List<CeloCurrencyDto>>

    @Query(
        "SELECT * FROM CeloCurrency " +
                "WHERE CeloCurrency.ChainId = :chainId " +
                "AND CeloCurrency.ChainName = :chainName"
    )
    fun currenciesInChain(chainId: Int, chainName: String = ChainName.Celo.name): Flow<List<CeloCurrencyDto>>
    fun currenciesInMainNet() = currenciesInChain(ChainMainNet.Celo.id)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg celoCurrency: CeloCurrencyDto)

    @Update
    fun update(vararg celoCurrency: CeloCurrencyDto)
}