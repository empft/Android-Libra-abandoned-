package com.example.libraandroid.ui.balance

import com.example.libraandroid.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BalanceByWalletNameRow(walletName: String, chainName: String, address: String) {
    val textStyleSmall = MaterialTheme.typography.caption

    Column(
        modifier = Modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.scr_balance__list__vertical_padding),
                horizontal = dimensionResource(id = R.dimen.scr_balance__list__horizontal_padding)
            )
    ) {
        Text(text = walletName, style = MaterialTheme.typography.h5)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = chainName,
                maxLines = 1,
                style = textStyleSmall,
                modifier = Modifier.weight(2f, false)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = address,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = textStyleSmall,
                modifier = Modifier.weight(1f, false)
            )
        }
    }
}

@Composable
fun BalanceByWalletRow(currency: String, amount: String) {
    val textStyle = MaterialTheme.typography.body1
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.scr_balance__list__vertical_padding),
                horizontal = dimensionResource(id = R.dimen.scr_balance__list__horizontal_padding)
    ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currency,
            style = textStyle,
            modifier = Modifier.fillMaxWidth(
                BalanceConst.ListStartFraction
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = amount, style = textStyle)
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