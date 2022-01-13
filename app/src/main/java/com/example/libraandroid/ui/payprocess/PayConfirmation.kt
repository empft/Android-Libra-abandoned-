package com.example.libraandroid.ui.payprocess

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

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