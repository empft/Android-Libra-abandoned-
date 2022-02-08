package com.example.libraandroid.ui.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.ui.transactionhistory.Transaction

@Composable
fun PaymentScreen(
    focusedBalances: List<String>,
    transactions: List<Transaction>,
    onAddConvert: () -> Unit,
    onExpandBalance: () -> Unit,
    onPayQr: () -> Unit,
    onPayManual: () -> Unit,
    onPayNfc: () -> Unit,
    onExpandTransaction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val verticalSpace = 20.dp

    Column(
        verticalArrangement = Arrangement.spacedBy(verticalSpace),
        modifier = modifier
    ) {
        PaymentBalance(
            listOf("$20.00", "30$30", "c$100")
            , onClickAddConvert = onAddConvert, onExpand = onExpandBalance,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
        )

        PayGroup(
            onQr = onPayQr,
            onDirect = onPayManual,
            onNfc = onPayNfc,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        PaymentTransactionHistory(
            transactions = transactions, onExpand = onExpandTransaction, modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        )
    }
}

@Preview
@Composable
fun PreviewPaymentScreen() {
    PaymentScreen(
        focusedBalances = listOf(),
        transactions = listOf(),
        onAddConvert = {},
        onExpandBalance = {},
        onExpandTransaction = {},
        onPayManual = {},
        onPayNfc = {},
        onPayQr = {}
    )
}