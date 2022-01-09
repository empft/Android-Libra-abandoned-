package com.example.libraandroid.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.libraandroid.service.network.StatelessClient
import com.example.libraandroid.service.session.AccountSessionLoginManager
import com.example.libraandroid.service.session.AccountSessionRepository
import com.example.libraandroid.ui.forgetlogin.*
import com.example.libraandroid.ui.register.RegisterViewModelFactory
import com.example.libraandroid.ui.register.registerInvitationNav

enum class LoginNav {
    ForgetLogin,
    Registration,
    Login
}

fun NavGraphBuilder.loginNavGraph(
    route: String,
    navController: NavHostController,
    onEnterSuccess: () -> Unit
) {
    val usernameState = mutableStateOf("")

    navigation(startDestination = LoginNav.Login.name, route = route) {
        composable(LoginNav.Login.name) {
            val context = LocalContext.current
            val loginViewModel: LoginViewModel = viewModel(
                factory =  LoginViewModelFactory(AccountSessionLoginManager(
                    repo = AccountSessionRepository(context),
                    network = StatelessClient.loginService
                ))
            )

            LoginScreen(
                usernameState = usernameState,
                loginState = loginViewModel.loginResult,
                onClickForget = {
                    navController.navigate(LoginNav.ForgetLogin.name)
                },
                onClickRegister = {
                    navController.navigate(LoginNav.Registration.name)
                },
                onClickLogin = loginViewModel::login,
                onLoginSuccess = {
                    onEnterSuccess()
                }
            )
        }

        registerInvitationNav(
            route = LoginNav.Registration.name,
            navController = navController,
            onRegisterSuccess = onEnterSuccess
        )
        // May switch to this in the future
        /*
        registerNav(
            route = LoginNav.Registration.name,
            navController = navController,
            onRegisterSuccess = onLoginSuccess
        )
        */

        forgetLoginNavGraph(
            LoginNav.ForgetLogin.name,
            navController,
            usernameState
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewLoginNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start") {
        loginNavGraph(
            route = "start",
            navController = navController,
            onEnterSuccess = {}
        )
    }
}

