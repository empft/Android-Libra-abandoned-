package com.example.libraandroid.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.libraandroid.domain.account.login.LoginInteractor
import com.example.libraandroid.domain.applicationsession.SessionManager
import com.example.libraandroid.domain.applicationsession.SessionRepository
import com.example.libraandroid.domain.applicationsession.ApplicationLoginInteractor
import com.example.libraandroid.network.StatelessClient
import com.example.libraandroid.ui.forgetlogin.*
import com.example.libraandroid.ui.register.registerInvitationNav

enum class LoginNav {
    ForgetLogin,
    Registration,
    Login
}

fun NavGraphBuilder.loginNavGraph(
    route: String,
    navController: NavHostController
) {
    val usernameState = mutableStateOf("")

    navigation(startDestination = LoginNav.Login.name, route = route) {
        composable(LoginNav.Login.name) {
            val context = LocalContext.current
            val sessionManager = SessionManager(SessionRepository(context))
            val loginViewModel: LoginViewModel = viewModel(
                factory =  LoginViewModelFactory(
                    ApplicationLoginInteractor(
                        sessionManager,
                        LoginInteractor(StatelessClient.login)
                    )
                )
            )

            LoginScreen(
                usernameState = usernameState,
                loginState = loginViewModel.loginFailure,
                onClickForget = {
                    navController.navigate(LoginNav.ForgetLogin.name)
                },
                onClickRegister = {
                    navController.navigate(LoginNav.Registration.name)
                },
                onClickLogin = loginViewModel::login,
                onClickGuestLogin = loginViewModel::guestLogin
            )
        }

        registerInvitationNav(
            route = LoginNav.Registration.name,
            navController = navController
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
            navController = navController
        )
    }
}

