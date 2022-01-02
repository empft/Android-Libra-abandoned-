package com.example.libraandroid.ui.balance

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

@Composable
fun BalanceByCurrencyChainRow(chainName: String) {
    Text(
        text = chainName, style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.scr_balance__list__vertical_padding),
                horizontal = dimensionResource(id = R.dimen.scr_balance__list__horizontal_padding)
            )
    )
}

@Composable
fun BalanceByCurrencyAmountTotalRow(
    currency: String, amount: String
) {
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
                BalanceUi.ListStartFraction
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = amount, style = textStyle)
    }
}

@Composable
fun BalanceByCurrencyAmountWalletRow(
    walletName: String, amount: String
) {
    val textStyle = MaterialTheme.typography.body2
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
            text = walletName,
            style = textStyle,
            modifier = Modifier.fillMaxWidth(
                BalanceUi.ListStartFraction
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = amount, style = textStyle)
    }
}


@Preview
@Composable
fun PreviewBalanceByCurrencyChainRow() {
    BalanceByCurrencyChainRow(
        chainName = "MainNet"
    )
}

@Preview
@Composable
fun PreviewBalanceByCurrencyAmountTotalRow() {
    BalanceByCurrencyAmountTotalRow(
        currency = "CELO", amount = "10000000000000"
    )
}

@Preview
@Composable
fun PreviewBalanceByCurrencyAmountWalletRow() {
    BalanceByCurrencyAmountWalletRow(walletName = "MyWallet", amount = "100087544635786373883887900")
}