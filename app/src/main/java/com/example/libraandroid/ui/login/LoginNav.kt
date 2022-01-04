package com.example.libraandroid.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.libraandroid.service.network.StatelessClient
import com.example.libraandroid.service.session.AccountSessionLoginManager
import com.example.libraandroid.service.session.AccountSessionRepository
import com.example.libraandroid.ui.forgetlogin.ForgetLoginNavHost
import com.example.libraandroid.ui.forgetlogin.ForgetLoginViewModel
import com.example.libraandroid.ui.register.RegisterInvitationNavHost
import com.example.libraandroid.ui.register.RegisterViewModelFactory

enum class LoginNav {
    ForgetLogin,
    Registration,
    Login
}

@Composable
fun LoginNavHost(
    onEnterSuccess: () -> Unit
) {
    val navController = rememberNavController()
    val usernameState = remember { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = LoginNav.Login.name
    ) {
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

        composable(LoginNav.Registration.name) {
            RegisterInvitationNavHost(
                onRegisterSuccess = onEnterSuccess,
                registerViewModel = viewModel(
                    factory = RegisterViewModelFactory(
                        StatelessClient.registrationService
                    )
                )
            )

            // May switch to this in the future
            /*
            RegisterNavHost(
                onRegisterSuccess = onLoginSuccess
            )
            */
        }

        composable(LoginNav.ForgetLogin.name) {
            val forgetLoginViewModel: ForgetLoginViewModel = viewModel()

            ForgetLoginNavHost(
                usernameState = usernameState,
                onForgetUsername = forgetLoginViewModel::sendUsernameReminder,
                onForgetPassword = forgetLoginViewModel::sendPasswordResetCode,
                onResetPassword = forgetLoginViewModel::resetPassword
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewLoginNavHost() {
    LoginNavHost(
        onEnterSuccess = {}
    )
}

