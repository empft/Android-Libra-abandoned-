package com.example.libraandroid.ui.balance

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R

@Composable
fun BalanceScreen(
    balances: List<Balance>,
    onSortAndFilter: () -> Unit,
    balanceViewMode: BalanceViewMode,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.scr_balance__text__top_bar_title))
        }, actions = {
            IconButton(onClick = onSortAndFilter) {
                Icon(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = stringResource(R.string.scr_balance__text__sort_and_filter)
                )
            }
        })
    }) {
        when(balanceViewMode) {
            BalanceViewMode.Currency -> {
                BalanceByCurrency(
                    balances = balances
                )
            }
            BalanceViewMode.Wallet -> {
                BalanceByWallet(
                    balances = balances
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewBalanceScreen() {
    BalanceScreen(
        balances = listOf(),
        onSortAndFilter = {},
        balanceViewMode = BalanceViewMode.Currency
    )
}