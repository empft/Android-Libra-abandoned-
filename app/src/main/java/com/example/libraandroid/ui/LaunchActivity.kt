package com.example.libraandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.viewbinding.BuildConfig
import com.example.libraandroid.service.session.AccountSessionRepository
import com.example.libraandroid.ui.login.LoginNavHost
import com.example.libraandroid.ui.login.LoginScreen
import com.example.libraandroid.ui.theme.VanillaTheme
import timber.log.Timber

class LaunchActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setContent {
            val session = AccountSessionRepository(LocalContext.current)
            val scaffoldState = rememberScaffoldState()
            val launchViewModel: LaunchViewModel by viewModel()

            VanillaTheme {
                Scaffold(scaffoldState = scaffoldState) {
                    LaunchedEffect(true) {
                        if (session.exist()) {
                            launchViewModel.login()
                        } else {
                            launchViewModel.logout()
                        }
                    }
                    LaunchScreen(
                        showLoggedIn = launchViewModel.isLoggedIn.value,
                        splashScreen = {},
                        loginScreen = { LoginNavHost() },
                        mainScreen = {}
                    )
                }
            }
        }
    }
}

@Composable
fun LaunchScreen(
    showLoggedIn: LaunchState,
    splashScreen: @Composable () -> Unit,
    loginScreen: @Composable () -> Unit,
    mainScreen: @Composable () -> Unit
) {
    when(showLoggedIn) {
        LaunchState.Loading -> splashScreen()
        LaunchState.LoggedIn -> mainScreen()
        LaunchState.LoggedOut -> loginScreen()
    }
}

@Preview
@Composable
fun PreviewNotLoggedInLaunchScreen() {
    LaunchScreen(
        showLoggedIn = LaunchState.LoggedOut,
        splashScreen = {},
        loginScreen = { LoginNavHost() },
        mainScreen = {}
    )
}

@Preview
@Composable
fun PreviewLoggedInScreen() {
    LaunchScreen(
        showLoggedIn = LaunchState.LoggedIn,
        splashScreen = {},
        loginScreen = { LoginNavHost() },
        mainScreen = {}
    )
}

@Preview
@Composable
fun PreviewLoadingScreen() {
    LaunchScreen(
        showLoggedIn = LaunchState.Loading,
        splashScreen = {},
        loginScreen = { LoginNavHost() },
        mainScreen = {}
    )
}