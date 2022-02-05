package com.example.libraandroid.ui.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.mainNav(
    route: String,
    navController: NavHostController
) {
    navigation(startDestination = MainNav.Payment.route, route = route) {
        composable(MainNav.Payment.route) {

        }
    }
}