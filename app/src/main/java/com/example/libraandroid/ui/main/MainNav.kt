package com.example.libraandroid.ui.main

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.libraandroid.R
import com.example.libraandroid.ui.payment.PaymentScreen

fun NavGraphBuilder.mainNav(
    route: String,
    navController: NavHostController
) {
    navigation(startDestination = MainNav.Payment.route, route = route) {
        composable(MainNav.Payment.route) {

        }
    }
}