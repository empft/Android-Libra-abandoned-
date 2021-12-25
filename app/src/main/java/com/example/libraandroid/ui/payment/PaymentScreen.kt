package com.example.libraandroid.ui.payment

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.ui.transactionhistory.Transaction

@Composable
fun PaymentScreen(
    transactions: List<Transaction>
) {
    val verticalSpace = 20.dp
    Column(
        verticalArrangement = Arrangement.spacedBy(verticalSpace)
    ) {
        PaymentBalance(
            listOf("$20.00", "30$30", "c$100")
            , onClickConvert = {

            }, onNavigate =  {

            },
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
        )

        Pay(
            onClickQr = {},
            onClickDirect = {},
            onClickNfc = {},
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        PaymentTransactionHistory(
            transactions = transactions, onNavigate = {
                
            }, modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewPaymentScreen() {
    PaymentScreen(
        transactions = listOf()
    )
}