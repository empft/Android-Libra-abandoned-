package com.example.libraandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
fun NavBackStackEntry.rememberParentEntry(navController: NavController): NavBackStackEntry {
    // First, get the parent of the current destination
    // This always exists since every destination in your graph has a parent
    val parentId = destination.parent!!.id

    // Now get the NavBackStackEntry associated with the parent
    return remember {
        navController.getBackStackEntry(parentId)
    }
}

@Composable
fun NavController.rememberBackStackEntry(route: String): NavBackStackEntry {
    return remember {
        getBackStackEntry(route)
    }
}