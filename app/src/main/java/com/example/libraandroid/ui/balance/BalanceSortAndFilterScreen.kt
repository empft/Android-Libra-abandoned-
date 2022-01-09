package com.example.libraandroid.ui.balance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R
import com.example.libraandroid.ui.button.OutlineButtonToggleGroup
import com.example.libraandroid.ui.filtersort.FilterSortRow

@Composable
fun BalanceSortAndFilterScreen(
    balanceViewMode: BalanceViewMode,
    onChangeBalanceViewMode: (BalanceViewMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(stringResource(id = R.string.scr_balance__text__sort_and_filter))
        })
    }, modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            FilterSortRow(title = { Text(stringResource(R.string.scr_balance__text__sort_by_title)) }) {
                OutlineButtonToggleGroup(selected = balanceViewMode, onClick = onChangeBalanceViewMode) {
                    when(it) {
                        BalanceViewMode.Currency -> {
                            Text(stringResource(R.string.scr_balance__text__view_mode_currency))
                        }
                        BalanceViewMode.Wallet -> {
                            Text(stringResource(R.string.scr_balance__text__view_mode_wallet))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBalanceSortAndFilterScreen() {
    val viewMode = remember {
        mutableStateOf(BalanceViewMode.Currency)
    }

    BalanceSortAndFilterScreen(viewMode.value, viewMode.component2())
}