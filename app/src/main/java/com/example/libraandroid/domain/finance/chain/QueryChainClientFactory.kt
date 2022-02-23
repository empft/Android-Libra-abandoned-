package com.example.libraandroid.domain.finance.chain

import com.diem.DiemClient
import com.diem.jsonrpc.DiemJsonRpcClient
import com.diem.types.ChainId
import com.example.libraandroid.domain.finance.celo.SimpleCeloClient
import com.example.libraandroid.domain.finance.diem.SimpleDiemClient
import org.celo.contractkit.ContractKit
import org.celo.contractkit.ContractKitOptions
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jService
import org.web3j.protocol.http.HttpService

class QueryChainClientFactory(
    private val chainRepository: ChainRepository
): ChainClient.Factory {
    override suspend fun celoClient(chainId: Int): ChainClient.Celo {
        val urls = chainRepository.chainUrls(ChainName.Celo, chainId)

        return SimpleCeloClient(kit = ContractKit(
            Web3j.build(HttpService(urls.first())),
            ContractKitOptions.Builder()
                .setChainId(chainId.toLong())
                .build()
        ))
    }

    override suspend fun diemClient(chainId: Int): ChainClient.Diem {
        val urls = chainRepository.chainUrls(ChainName.Diem, chainId)

        return SimpleDiemClient(
            DiemJsonRpcClient(url, ChainId(chainId.toByte()))
        )
    }
}