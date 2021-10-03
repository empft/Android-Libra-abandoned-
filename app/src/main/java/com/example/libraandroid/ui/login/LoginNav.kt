package com.example.libraandroid.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.libraandroid.ui.LaunchViewModel
import com.example.libraandroid.ui.forgetlogin.ForgetLoginNav
import com.example.libraandroid.ui.forgetlogin.ForgetLoginNavHost
import kotlinx.coroutines.flow.flow

enum class LoginNav {
    ForgetLogin,
    Registration,
    Login
}

@Composable
fun LoginNavHost() {
    val navController = rememberNavController()
    val usernameState = remember { mutableStateOf("") }
    val launchViewModel: LaunchViewModel = viewModel()

    LoginNavHostInverted(
        navController = navController,
        onClickLogin = {_, _ ->}
    ) {

    }
}

// Hoist the state and function so it can be previewed
// Cannot preview nav host :(
@Composable
fun LoginNavHostInverted(
    navController: NavHostController = rememberNavController(),
    usernameState: MutableState<String> = remember { mutableStateOf("") },
    onClickLogin: (username: String, password: String) -> Unit,
    onLoginSuccess: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = LoginNav.Login.name
    ) {
        composable(LoginNav.Login.name) {
            LoginScreen(
                usernameState = usernameState,
                loginState = flow {
                    emit(LoginResult.Empty)
                },
                onClickForget = { navController.navigate(LoginNav.ForgetLogin.name) },
                onClickRegister = { navController.navigate(LoginNav.Registration.name) },
                onClickLogin = onClickLogin,
                onLoginSuccess = {
                    onLoginSuccess()
                }
            )
        }

        navigation(startDestination = "", route = "") {

        }

        composable(LoginNav.Registration.name) {

        }

        composable(LoginNav.ForgetLogin.name) {
            ForgetLoginNavHost(
                navController = navController,
                usernameState = remember { mutableStateOf("") }
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewLoginNavHost() {
    LoginNavHostInverted(
        onClickLogin = {_, _ -> },
        onLoginSuccess = {}
    )
}

