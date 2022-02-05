package com.example.libraandroid.ui.payment

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

enum class PaymentNav {
    Home,
    Balance,
    History,
    Wallet,
    Pay
}

fun NavGraphBuilder.paymentNav(
    route: String,
    navController: NavHostController
) {
    navigation(startDestination = PaymentNav.Home.name, route = route) {
        composable(PaymentNav.Home.name) {

        }

        composable(PaymentNav.Balance.name) {

        }


    }
}
