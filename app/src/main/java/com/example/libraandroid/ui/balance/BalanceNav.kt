package com.example.libraandroid.ui.balance

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

            BalanceScreen(
                balances = balanceViewModel.balances,
                onSortAndFilter = {
                    navController.navigate(BalanceNav.Filters.name)
                },
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