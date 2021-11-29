package com.example.libraandroid.ui.currency

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.ui.transactionhistory.Transaction
import com.example.libraandroid.ui.transactionhistory.TransactionDirection

@Composable
fun TransactionAmount(
    transaction: Transaction.Diem,
    selfAddress: String,
    symbol: String = ""
) {
    val amount = formatAmount(
        value = transaction.amount, decimalPlaces = CurrencyConstant.DIEM_DECIMAL,
        symbol = symbol
    )
    when(transaction.direction(selfAddress)) {
        TransactionDirection.Neither -> {
            Text(amount)
        }
        TransactionDirection.Recipient -> {
            Text(amount)
        }
        TransactionDirection.Self -> {
            Text(amount)
        }
        TransactionDirection.Sender -> {
            Text(amount)
        }
    }
}

@Preview
@Composable
fun PreviewTransactionAmount() {

}