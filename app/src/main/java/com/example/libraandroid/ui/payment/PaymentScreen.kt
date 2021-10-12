package com.example.libraandroid.ui.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PaymentScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Balance(
            listOf("$20.00", "30$30", "c$100"),{}, {},
            modifier = Modifier.padding(all = 16.dp)
        )

        Pay(
            onClickQr = {},
            onClickDirect = {},
            onClickNfc = {}
        )

        PaymentHistory()
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewPaymentScreen() {
    PaymentScreen()
}