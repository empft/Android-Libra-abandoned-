package com.example.libraandroid.domain.transaction

import java.math.BigInteger

sealed interface Transaction {
    data class Celo(
        val blockHash: String,
        val blockNumber: ULong,
        // currency
        val contractAddress: String,
        val cumulativeGasUsed: BigInteger,
        val from: String,
        val gas: ULong,
        val gasPrice: BigInteger,
        val gasUsed: ULong,
        val hash: String,
        val input: String,
        val logIndex: Int,
        val nonce: ULong,
        val timestamp: String,
        val to: String,
        val tokenDecimal: Int,
        val tokenName: String,
        val tokenSymbol: String,
        val transactionIndex: Int,
        val value: BigInteger
    ): Transaction

    data class Diem(
        val bytes: String,
        val gasUsed: ULong,
        val hash: String,
        val version: ULong,
        val vmStatus: VmStatus,
        val transactionData: TransactionData,
    ): Transaction {

        data class TransactionData(
            val type: String,
            val timestamp: ULong,
            val sender: String,
            val signatureScheme: String,
            val signature: String,
            val publicKey: String,
            val sequenceNumber: ULong,
            val chainId: UInt,
            val maxGasAmount: ULong,
            val gasUnitPrice: ULong,
            val gasCurrency: String,
            val expirationTimestamp: ULong,
            val scriptHash: String,
            val scriptBytes: String,
            val script: Script
        ) {
            data class Script(
                val type: String,
                val code: String,
                val arguments: List<String>,
                val typeArguments: List<String>,
                // hex-encoded receiver account address bytes
                val receiver: String,
                // peer to peer transfer amount
                val amount: ULong? = null,
                // peer to peer transfer currency code
                val currency: String? = null,
                val metadata: String,
                val metadataSignature: String,
                val moduleAddress: String? = null,
                val moduleName: String? = null,
                val functionName: String? = null,
                val argumentsBcs: List<String>? = null
            )
        }

        data class VmStatus(
            val type: String,
            val location: String? = null,
            val abortCode: ULong? = null,
            val functionIndex: UInt? = null,
            val codeOffset: UInt? = null,
            val explanation: Explanation? = null
        ) {
            data class Explanation(
                val category: String,
                val categoryDescription: String,
                val reason: String,
                val reasonDescription: String
            )
        }
    }
}