package com.example.libraandroid.ui.currency

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.libraandroid.ui.theme.negativeColor
import com.example.libraandroid.ui.theme.positiveColor
import com.example.libraandroid.ui.transactionhistory.Transaction
import com.example.libraandroid.ui.transactionhistory.TransactionDirection

@Composable
fun DiemTransferAmount(
    transaction: Transaction.Diem,
    selfAddress: String,
) {
    when(transaction.direction(selfAddress)) {
        TransactionDirection.Neither -> {}
        TransactionDirection.Recipient -> {
            Text(formatAmount(
                value = transaction.amount, decimalPlaces = CurrencyConstant.DIEM_DECIMAL,
                currencyCode = transaction.currency, sign = "+"
            ), color = positiveColor())
        }
        TransactionDirection.Self -> {
            Text(formatAmount(
                value = transaction.amount, decimalPlaces = CurrencyConstant.DIEM_DECIMAL,
                currencyCode = transaction.currency, sign = "="
            ), color = Color.LightGray)
        }
        TransactionDirection.Sender -> {
            Text(formatAmount(
                value = transaction.amount, decimalPlaces = CurrencyConstant.DIEM_DECIMAL,
                currencyCode = transaction.currency
            ), color = negativeColor())
        }
    }
}

@Composable
fun CeloTransferAmount(
    transaction: Transaction.Celo.Transfer,
    selfAddress: String,
) {
    when(transaction.direction(selfAddress)) {
        TransactionDirection.Neither -> {}
        TransactionDirection.Recipient -> {
            Text(formatAmount(
                value = transaction.value, decimalPlaces = transaction.tokenDecimal,
                currencyCode = transaction.tokenSymbol, sign = "+"
            ), color = positiveColor())
        }
        TransactionDirection.Self -> {
            Text(formatAmount(
                value = transaction.value, decimalPlaces = transaction.tokenDecimal,
                currencyCode = transaction.tokenSymbol, sign = "="
            ), color = Color.LightGray)
        }
        TransactionDirection.Sender -> {
            Text(formatAmount(
                value = transaction.value, decimalPlaces = transaction.tokenDecimal,
                currencyCode = transaction.tokenSymbol
            ), color = negativeColor())
        }
    }
}