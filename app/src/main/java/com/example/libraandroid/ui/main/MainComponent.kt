package com.example.libraandroid.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.libraandroid.R

sealed class MainNav(val route: String, @StringRes val title: Int, @DrawableRes val icon: Int) {
    object Payment: MainNav("payment", R.string.scr_main__nav__payment, R.drawable.ic_wallet)
    object Chat: MainNav("chat", R.string.scr_main__nav__chat, R.drawable.ic_chat)
    object Nearby: MainNav("nearby", R.string.scr_main__nav__nearby, R.drawable.ic_nearby)
    object Setting: MainNav("setting", R.string.scr_main__nav__setting, R.drawable.ic_setting)
}

@Composable
fun MainTopBar(
    backgroundColor: Color
) {
    TopAppBar(
        backgroundColor = backgroundColor
    ) {
        Text("Test Title")
    }
}

@Composable
fun MainBottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = BottomNavigationDefaults.Elevation,
) {
    BottomNavigation(
        modifier,
        backgroundColor,
        contentColor,
        elevation
    ) {
        val items = listOf(
            MainNav.Payment,
            MainNav.Setting
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val iconSize = 24.dp

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(item.icon),
                        contentDescription = stringResource(item.title),
                        modifier = Modifier.size(iconSize)
                    )
                },
                label = { Text(text = stringResource(item.title)) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewMainTopBar() {
    val backgroundColor = MaterialTheme.colors.background
    MainTopBar(
        backgroundColor
    )
}

@Preview
@Composable
fun PreviewMainBottomNavigation() {
    MainBottomNavigation(
        navController = rememberNavController()
    )
}