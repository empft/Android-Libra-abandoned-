package com.example.libraandroid.domain.finance.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.libraandroid.domain.finance.chain.ChainName

@Dao
interface ChainEndpointDao {
    @Query("SELECT * FROM ChainEndpoint WHERE Name = :chainName AND Id = :chainId")
    suspend fun chainEndpoints(chainName: ChainName, chainId: Int): List<ChainEndpointDto>

    @Insert
    suspend fun insert(vararg chainEndpointDto: ChainEndpointDto)
}