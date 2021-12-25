package com.example.libraandroid.ui.payment

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.transactionhistory.Transaction
import com.example.libraandroid.ui.transactionhistory.TransactionHistory

@Composable
fun PaymentTransactionHistory(
    transactions: List<Transaction>,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExpandableSurface(
        title = stringResource(
        R.string.scr_pay__text__history
    ), onExpand = onNavigate, modifier = modifier) {
        TransactionHistory(
            transactions = transactions
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewPaymentTransactionHistory() {
    PaymentTransactionHistory(
        transactions = listOf(),
        {}
    )
}