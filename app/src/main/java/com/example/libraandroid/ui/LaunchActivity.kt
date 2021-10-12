package com.example.libraandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.viewbinding.BuildConfig
import com.example.libraandroid.service.session.AccountSessionRepository
import com.example.libraandroid.ui.login.LoginNavHost
import com.example.libraandroid.ui.theme.VanillaTheme
import timber.log.Timber

class LaunchActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launchViewModel: LaunchViewModel by viewModels()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setContent {
            VanillaTheme {
                val session = AccountSessionRepository(LocalContext.current)

                LaunchedEffect(true) {
                    if (session.exist()) {
                        launchViewModel.login()
                    } else {
                        launchViewModel.logout()
                    }
                }
                Surface {
                    LaunchScreen(
                        showLoggedIn = launchViewModel.isLoggedIn.value,
                        splashScreen = {},
                        loginScreen = { LoginNavHost(
                            onEnterSuccess = {
                                launchViewModel.login()
                            }
                        )},
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

@Composable
private fun LaunchScreenForPreview(showLoggedIn: LaunchState) {
    LaunchScreen(
        showLoggedIn = LaunchState.LoggedOut,
        splashScreen = {},
        loginScreen = { LoginNavHost(
            onEnterSuccess = {}
        ) },
        mainScreen = {}
    )
}

@Preview
@Composable
fun PreviewNotLoggedInLaunchScreen() {
    LaunchScreenForPreview(showLoggedIn = LaunchState.LoggedOut)
}

@Preview
@Composable
fun PreviewLoggedInScreen() {
    LaunchScreenForPreview(showLoggedIn = LaunchState.LoggedIn)
}

@Preview
@Composable
fun PreviewLoadingScreen() {
    LaunchScreenForPreview(showLoggedIn = LaunchState.Loading)
}