package com.example.libraandroid.ui.transactionhistory

import kotlinx.serialization.Serializable
import java.math.BigInteger
import java.time.Instant

data class SenderNote(
    val id: Long,
    val name: String? = null,
    val receipt: List<ReceiptItem>? = null
) {
    data class ReceiptItem(
        val item: String,
        val amount: BigInteger,
        val quantity: Long,
    )
}

data class RecipientNote(
    val id: Long
)

data class AddressWithId(
    val id: Long? = null,
    val name: String? = null,
    val address: String
)

sealed interface Transaction {
    data class Celo(
        val blockHash: String,
        val blockNumber: ULong,
        val cumulativeGasUsed: BigInteger,
        val hash: String,
        val input: String,
        val nonce: ULong,

        val timestamp: Instant,

        val gatewayFee: BigInteger,
        val feeRecipient: String,
        val gatewayCurrency: String,
        val gatewayCurrencyDecimal: Int,
        val gatewayCurrencySymbol: String,

        val gas: ULong,
        val gasPrice: BigInteger,
        val gasUsed: ULong,
        val gasDecimal: Int,

        val tokenTransfer: List<Transfer>,

        val senderNote: SenderNote? = null,
        val recipientNote: RecipientNote? = null
    ): Transaction {
        data class Transfer(
            val transactionIndex: Int,
            val logIndex: Int,
            val from: AddressWithId,
            val to: AddressWithId,
            val value: BigInteger,

            // token/currency address
            val contractAddress: String,
            val tokenDecimal: Int,
            val tokenName: String,
            val tokenSymbol: String,
        )
    }

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

        val senderNote: SenderNote? = null,
        val recipientNote: RecipientNote? = null
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

@Serializable
data class RawCelo(
    val blockHash: String,
    val blockNumber: ULong,
    val cumulativeGasUsed: String,
    val hash: String,
    val input: String,
    val nonce: ULong,

    val timestamp: Long,

    val gatewayFee: String,
    val feeRecipient: String,
    val gatewayCurrency: String,

    val gas: ULong,
    val gasPrice: String,
    val gasUsed: ULong,

    val tokenTransfer: List<RawTransfer>,
) {
    @Serializable
    data class RawTransfer(
        val transactionIndex: Int,
        val logIndex: Int,
        val from: String,
        val to: String,
        val value: String,

        // token/currency address
        val contractAddress: String,
        val tokenDecimal: Int,
        val tokenName: String,
        val tokenSymbol: String,
    )

    companion object {
        fun from(tx: Transaction.Celo): RawCelo {
            return RawCelo(
                blockHash = tx.blockHash,
                blockNumber = tx.blockNumber,
                cumulativeGasUsed = tx.cumulativeGasUsed.toString(),
                hash = tx.hash,
                input = tx.input,
                nonce = tx.nonce,
                timestamp = tx.timestamp.epochSecond,
                gatewayFee = tx.gatewayFee.toString(),
                feeRecipient = tx.feeRecipient,
                gatewayCurrency = tx.gatewayCurrency,
                gas = tx.gas,
                gasPrice = tx.gasPrice.toString(),
                gasUsed = tx.gasUsed,
                tokenTransfer = tx.tokenTransfer.map {
                    RawTransfer(
                        transactionIndex = it.transactionIndex,
                        logIndex = it.logIndex,
                        from = it.from.address,
                        to = it.to.address,
                        value = it.value.toString(),
                        contractAddress = it.contractAddress,
                        tokenDecimal = it.tokenDecimal,
                        tokenName = it.tokenName,
                        tokenSymbol = it.tokenSymbol
                    )
                }
            )
        }
    }
}