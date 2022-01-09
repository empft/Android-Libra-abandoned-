package com.example.libraandroid.ui.forgetlogin

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.libraandroid.R
import com.example.libraandroid.ui.navigation.rememberBackStackEntry
import com.example.libraandroid.ui.navigation.rememberParentEntry

enum class ForgetLoginNav {
    ForgetChoice,
    ForgetUsername,
    ForgetPassword,
    ResetPassword
}

fun NavGraphBuilder.forgetLoginNavGraph(
    route: String,
    navController: NavHostController,
    usernameState: MutableState<String>
) {
    val emailState = mutableStateOf("")

    @Composable
    fun formModifier(): Modifier {
        return Modifier.padding(
            horizontal = dimensionResource(R.dimen.g__form__horizontal_margin)
        )
    }

    navigation(startDestination = ForgetLoginNav.ForgetChoice.name, route = route) {
        composable(ForgetLoginNav.ForgetChoice.name) {
            ForgetChoiceScreen(
                onClickForgetUsername = {
                    navController.navigate(ForgetLoginNav.ForgetUsername.name)
                },
                onClickForgetPassword = {
                    navController.navigate(ForgetLoginNav.ForgetPassword.name)
                },
                onClickResetPassword = {
                    navController.navigate(ForgetLoginNav.ResetPassword.name)
                }
            )
        }

        composable(ForgetLoginNav.ForgetUsername.name) {
            val forgetLoginViewModel = viewModel<ForgetLoginViewModel>(
                it.rememberParentEntry(navController = navController)
            )

            ForgetUsernameScreen(
                emailState = emailState,
                modifier = formModifier(),
                onClick = forgetLoginViewModel::sendUsernameReminder
            )
        }

        composable(ForgetLoginNav.ForgetPassword.name) {
            val forgetLoginViewModel = viewModel<ForgetLoginViewModel>(
                it.rememberParentEntry(navController = navController)
            )

            ForgetPasswordScreen(
                emailState = emailState,
                usernameState = usernameState,
                modifier = formModifier(),
                onClick = forgetLoginViewModel::sendPasswordResetCode
            )
        }

        composable(ForgetLoginNav.ResetPassword.name) {
            val forgetLoginViewModel = viewModel<ForgetLoginViewModel>(
                it.rememberParentEntry(navController = navController)
            )

            ResetPasswordScreen(
                modifier = formModifier(),
                onClick = forgetLoginViewModel::resetPassword
            )
        }
    }
}

@Preview
@Composable
fun PreviewForgetLoginNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        forgetLoginNavGraph("start", navController, mutableStateOf(""))
    }
}

