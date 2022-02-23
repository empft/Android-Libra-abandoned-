package com.example.libraandroid.domain.finance.diem

import com.diem.DiemClient
import com.diem.PrivateKey
import com.diem.Signer
import com.diem.types.SignedTransaction
import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.chain.ChainClient
import java.math.BigInteger

class SimpleDiemClient(
    private val client: DiemClient
): ChainClient.Diem {
    /**
     * Returns map of currency name to amount
     */
    override fun balances(address: String): Map<String, BigInteger> {
        return client.getAccount(address).balancesList.associateBy({ it.currency },{ it.amount.toBigInteger() })
    }



}