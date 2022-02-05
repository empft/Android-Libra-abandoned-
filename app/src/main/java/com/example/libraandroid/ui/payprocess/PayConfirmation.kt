package com.example.libraandroid.ui.payprocess

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PayConfirmation(
    onPay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {





    }
}

data class PayInfo(
    val recipient: String,
    val amount: String
)

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewPayConfirmation() {
    PayConfirmation({})
}