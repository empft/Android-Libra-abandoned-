package com.example.libraandroid.ui.balance

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R

@Composable
internal fun SortAndFilterIconButton(
    onSortAndFilter: () -> Unit
) {
    IconButton(onClick = onSortAndFilter) {
        Icon(
            painter = painterResource(R.drawable.ic_filter),
            contentDescription = stringResource(R.string.scr_balance__text__sort_and_filter)
        )
    }
}

@Composable
fun BalanceOverview(
    balances: List<Balance>,
    balanceViewMode: BalanceViewMode,
    modifier: Modifier = Modifier
) {
    when(balanceViewMode) {
        BalanceViewMode.Currency -> {
            BalanceByCurrency(
                balances = balances,
                modifier = modifier
            )
        }
        BalanceViewMode.Wallet -> {
            BalanceByWallet(
                balances = balances,
                modifier = modifier
            )
        }
    }

}

@Preview
@Composable
fun PreviewBalanceOverview() {
    BalanceOverview(
        balances = listOf(),
        balanceViewMode = BalanceViewMode.Currency
    )
}