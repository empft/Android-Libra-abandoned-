package com.example.libraandroid.ui.payment

import java.math.BigInteger
import java.time.Instant

data class SenderNote(
    val id: Long,
    val name: String,
    val receipt: List<Receipt>? = null
) {
    data class Receipt(
        val item: String,
        val amount: BigInteger,
        val quantity: Long,
    )
}

data class RecipientNote(
    val id: Long
)

sealed interface Transaction {
    data class Celo(
        val gatewayFee: BigInteger,
        val gatewayRecipient: String,
        val gatewayCurrencyAddress: String,
        val gatewayCurrencyName: String,

        val gas: ULong,
        val gasPrice: BigInteger,
        val gasUsed: ULong,

        val transactionIndex: Int,
        val logIndex: Int,
        val nonce: ULong,
        val timestamp: Instant,
        val from: String,
        val to: String,
        val value: BigInteger,

        // token/currency address
        val contractAddress: String,
        val tokenDecimal: Int,
        val tokenName: String,
        val tokenSymbol: String
    ): Transaction

    data class Diem(
        val version: ULong,

        val vmStatus: VmStatus,

        val timestamp: ULong,
        val sender: String,
        val receiver: String,
        val publicKey: String,
        val sequenceNumber: ULong,
        val chainId: UInt,
        val maxGasAmount: ULong,
        val gasUnitPrice: ULong,
        val gasUsed: ULong,
        val gasCurrency: String,
        val expirationTimestamp: ULong,

        val amount: ULong,
        val currency: String,
        val metadata: String,
    ): Transaction {

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