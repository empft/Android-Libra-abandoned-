package com.example.libraandroid.ui.payment

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class PaymentNav {
    Home,
    Balance,
    History,
    Wallet,
    Pay
}

@Composable
fun PaymentNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController, startDestination = PaymentNav.Home.name, modifier = modifier
    ) {
        composable(PaymentNav.Home.name) {

        }

        composable(PaymentNav.Balance.name) {

        }
    }
}