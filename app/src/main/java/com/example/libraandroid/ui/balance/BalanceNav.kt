package com.example.libraandroid.ui.balance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class BalanceNav {
    Filters,
    Balance
}

@Composable
fun BalanceNavHost(
    balances: List<Balance>,
    balanceViewModeState: MutableState<BalanceViewMode>,
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = BalanceNav.Balance.name, modifier = modifier) {
        composable(BalanceNav.Balance.name) {
            BalanceScreen(
                balances = balances,
                onSortAndFilter = {
                    navController.navigate(BalanceNav.Filters.name)
                },
                balanceViewMode = balanceViewModeState.component1()
            )
        }

        composable(BalanceNav.Filters.name) {
            BalanceSortAndFilterScreen(
                balanceViewModeState = balanceViewModeState
            )
        }
    }
}