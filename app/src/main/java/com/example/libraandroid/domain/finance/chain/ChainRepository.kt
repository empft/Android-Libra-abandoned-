package com.example.libraandroid.domain.finance.chain

import com.example.libraandroid.domain.finance.database.ChainEndpointDao

class ChainRepository(
    private val chainEndpointDao: ChainEndpointDao
) {
    suspend fun chainUrls(chainName: ChainName, chainId: Int): List<String> {
        return chainEndpointDao
            .chainEndpoints(chainName, chainId)
            .map {
                it.url
            }
    }



}