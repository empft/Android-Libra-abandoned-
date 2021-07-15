package com.example.libraandroid.extension

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

/**
 * Check whether the current fragment is the source fragment before navigating
 */
fun NavController.navigateSafe(@IdRes currentFragmentId: Int, directions: NavDirections) {
    if (this.currentDestination?.id == currentFragmentId) {
        this.navigate(directions)
    }
}