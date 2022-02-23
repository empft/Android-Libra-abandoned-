package com.example.libraandroid.domain.finance.database

import androidx.room.*
import com.example.libraandroid.domain.finance.chain.ChainName
import kotlinx.coroutines.flow.Flow

@Dao
interface ChainDao {
    @Query("SELECT * FROM Chain WHERE Name = :chainName AND Id = :chainId")
    fun chain(chainName: String, chainId: Int): Flow<ChainDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg chainDto: ChainDto)

    @Update
    fun update(vararg chainDto: ChainDto)
}