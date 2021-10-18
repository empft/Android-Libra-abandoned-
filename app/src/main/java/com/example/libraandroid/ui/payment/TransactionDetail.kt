package com.example.libraandroid.ui.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import java.math.BigInteger

@Composable
fun TransactionDetail(
    transaction: Transaction
) {
    Column {
        when(transaction) {
            is Transaction.Celo -> {
                Text("Celo")
            }
            is Transaction.Diem -> {
                Text("Diem")
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewTransactionDetail() {
    TransactionDetail(transaction = Transaction.Celo(
        gatewayFee = BigInteger.ONE,
        gatewayRecipient = "",
        gatewayCurrencyAddress = "",
        gatewayCurrencyName = "",
        gas = 0UL,
        gasPrice = BigInteger.ONE,
        gasUsed = 0UL,

    ))
}