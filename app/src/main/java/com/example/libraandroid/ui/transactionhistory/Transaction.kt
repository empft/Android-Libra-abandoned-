package com.example.libraandroid.ui.transactionhistory

import com.example.libraandroid.ui.currency.CurrencyConstant
import com.example.libraandroid.ui.currency.TransferAmount
import com.example.libraandroid.ui.wallet.Wallet
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
                timestamp = tx.timestamp.toEpochMilli(),
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

@Serializable
data class RawDiem(
    val version: ULong,

    val vmStatus: RawVmStatus,

    val timestamp: Long,
    val sender: String,
    val receiver: String,
    val publicKey: String,
    val sequenceNumber: ULong,
    val chainId: UInt,
    val maxGasAmount: ULong,
    val gasUnitPrice: ULong,
    val gasUsed: ULong,
    val gasCurrency: String,
    val expirationTimestamp: Long,

    val amount: ULong,
    val currency: String,
    val metadata: String,

    ) {
    @Serializable
    data class RawVmStatus(
        val type: String,
        val location: String? = null,
        val abortCode: ULong? = null,
        val functionIndex: UInt? = null,
        val codeOffset: UInt? = null,
        val explanation: RawExplanation? = null
    ) {
        companion object {
            fun from(vm: Transaction.Diem.VmStatus): RawVmStatus {
                val explanation = if (vm.explanation != null) {
                    RawExplanation.from(vm.explanation)
                } else null

                return RawVmStatus(
                    type = vm.type,
                    location = vm.location,
                    abortCode = vm.abortCode,
                    functionIndex = vm.functionIndex,
                    codeOffset = vm.codeOffset,
                    explanation = explanation
                )
            }
        }

        @Serializable
        data class RawExplanation(
            val category: String,
            val categoryDescription: String,
            val reason: String,
            val reasonDescription: String
        ) {
            companion object {
                fun from(ex: Transaction.Diem.VmStatus.Explanation): RawExplanation {
                    return RawExplanation(
                        category = ex.category,
                        categoryDescription = ex.categoryDescription,
                        reason = ex.reason,
                        reasonDescription = ex.reasonDescription
                    )
                }
            }
        }
    }

    companion object {
        fun from(tx: Transaction.Diem): RawDiem {
            return RawDiem(
                version = tx.version,
                vmStatus = RawVmStatus.from(tx.vmStatus),
                timestamp = tx.timestamp.toEpochMilli(),
                sender = tx.sender.address,
                receiver = tx.receiver.address,
                publicKey = tx.publicKey,
                sequenceNumber = tx.sequenceNumber,
                chainId = tx.chainId,
                maxGasAmount = tx.maxGasAmount,
                gasUnitPrice = tx.gasUnitPrice,
                gasUsed = tx.gasUsed,
                gasCurrency = tx.gasCurrency,
                expirationTimestamp = tx.expirationTimestamp.toEpochMilli(),
                amount = tx.amount,
                currency = tx.currency,
                metadata = tx.metadata
            )
        }
    }
}

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
        val gasCurrencySymbol: String,

        val tokenTransfer: List<Transfer>,

        val senderNote: SenderNote? = null,
        val recipientNote: RecipientNote? = null
    ): Transaction {
        data class Transfer(
            val transactionIndex: Int,
            val logIndex: Int,
            val from: Wallet.Celo,
            val to: Wallet.Celo,
            val value: BigInteger,

            // token/currency address
            val contractAddress: String,
            val tokenDecimal: Int,
            val tokenName: String,
            val tokenSymbol: String,
            val viewer: TransactionViewer
        ): TransferAmount {
            override fun amount(): BigInteger = value
            override fun decimalPlaces(): Int = tokenDecimal
            override fun currency(): String = tokenSymbol
            override fun viewer(): TransactionViewer = viewer
        }
    }

    data class Diem(
        val version: ULong,

        val vmStatus: VmStatus,

        val timestamp: Instant,
        val sender: Wallet.Diem,
        val receiver: Wallet.Diem,
        val publicKey: String,
        val sequenceNumber: ULong,
        val chainId: UInt,
        val maxGasAmount: ULong,
        val gasUnitPrice: ULong,
        val gasUsed: ULong,
        val gasCurrency: String,
        val expirationTimestamp: Instant,

        val amount: ULong,
        val currency: String,
        val metadata: String,

        val senderNote: SenderNote? = null,
        val recipientNote: RecipientNote? = null,
        val viewer: TransactionViewer
    ): Transaction, TransferAmount {

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

        override fun amount(): BigInteger = BigInteger(amount.toString())
        override fun decimalPlaces(): Int = CurrencyConstant.DiemDecimal
        override fun currency(): String = currency
        override fun viewer(): TransactionViewer = viewer
    }
}