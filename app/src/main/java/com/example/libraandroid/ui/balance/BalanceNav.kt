package com.example.libraandroid.ui.balance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.libraandroid.ui.navigation.rememberParentEntry
import kotlinx.coroutines.launch

enum class BalanceNav {
    Filters,
    Balance
}

fun NavGraphBuilder.balanceNav(
    route: String,
    navController: NavHostController
) {
    @Composable
    fun balanceViewModel(
        viewModelStoreOwner: ViewModelStoreOwner
    ): BalanceViewModel {
        return viewModel(
            viewModelStoreOwner = viewModelStoreOwner,
            factory = BalanceViewModelFactory(
                settings = BalanceSettings(LocalContext.current)
            )
        )
    }

    navigation(startDestination = BalanceNav.Balance.name, route = route) {
        composable(BalanceNav.Balance.name) {
            val balanceViewModel = balanceViewModel(it.rememberParentEntry(
                navController
            ))

            BalanceOverview(
                balances = balanceViewModel.balances,
                balanceViewMode = balanceViewModel.balanceViewMode().value
            )
        }

        composable(BalanceNav.Filters.name) {
            val balanceViewModel = balanceViewModel(it.rememberParentEntry(
                navController
            ))

            val coroutineScope = rememberCoroutineScope()

            BalanceSortAndFilterScreen(
                balanceViewMode = balanceViewModel.balanceViewMode().value,
                onChangeBalanceViewMode = {
                    coroutineScope.launch {
                        balanceViewModel.balanceViewMode(it)
                    }
                }
            )
        }
    }
}