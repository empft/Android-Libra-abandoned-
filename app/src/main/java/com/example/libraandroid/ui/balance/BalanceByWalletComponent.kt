package com.example.libraandroid.ui.balance

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BalanceByWalletNameRow(walletName: String, chainName: String, address: String) {
    Column {
        Text(text = walletName, style = MaterialTheme.typography.h5)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = chainName,
                maxLines = 1,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.weight(2f, false)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = address,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.weight(1f, false)
            )
        }
    }
}

@Composable
fun BalanceByWalletRow(currency: String, amount: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currency,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth(0.3f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = amount, style = MaterialTheme.typography.h6)
    }
}

@Preview
@Composable
fun PreviewBalanceByWalletNameRow() {
    BalanceByWalletNameRow(
        walletName = "MyWallet", chainName = "MainNet", address = "2f4de44323fabe730"
    )
}

@Preview
@Composable
fun PreviewBalanceByWalletRow() {
    BalanceByWalletRow(currency = "CELO", amount = "100,000,001,000,000,010")
}