package com.example.libraandroid.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.viewbinding.BuildConfig
import com.example.libraandroid.ui.login.loginNavGraph
import com.example.libraandroid.ui.theme.VanillaTheme
import timber.log.Timber

class LaunchActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launchViewModel: LaunchViewModel by viewModels()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setContent {
            VanillaTheme {
                val loginState = launchViewModel.isLoggedIn.collectAsState(
                    initial = LaunchState.Loading
                )

                Surface {
                    LaunchScreen(
                        showLoggedIn = loginState.value,
                        splashScreen = {},
                        loginScreen = {
                            LoginNavHost()
                        },
                        mainScreen = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginNavHost() {
    val navController = rememberNavController()
    val route = "Start"
    NavHost(navController = navController, startDestination = route) {
        loginNavGraph(
            route,
            navController
        )
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
        showLoggedIn = showLoggedIn,
        splashScreen = {},
        loginScreen = {
            LoginNavHost()
        },
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