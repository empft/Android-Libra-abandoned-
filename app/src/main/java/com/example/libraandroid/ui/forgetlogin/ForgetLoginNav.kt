package com.example.libraandroid.ui.forgetlogin

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.libraandroid.R

enum class ForgetLoginNav {
    ForgetChoice,
    ForgetUsername,
    ForgetPassword,
    ResetPassword
}

@Composable
fun ForgetLoginNavHost(
    usernameState: MutableState<String>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val formModifier = Modifier.padding(
        horizontal = dimensionResource(R.dimen.form_horizontal_margin)
    )

    val emailState = remember { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = ForgetLoginNav.ForgetChoice.name,
        modifier = modifier
    ) {
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
            ForgetUsernameScreen(
                emailState = emailState,
                modifier = formModifier) {

            }
        }

        composable(ForgetLoginNav.ForgetPassword.name) {
            ForgetPasswordScreen(
                emailState = emailState,
                usernameState = usernameState,
                modifier = formModifier) { email, username ->

            }
        }

        composable(ForgetLoginNav.ResetPassword.name) {
            ResetPasswordScreen(modifier = formModifier) { token, password ->

            }
        }
    }
}

@Preview
@Composable
fun PreviewForgetLoginNavHost() {
    val usernameState = remember { mutableStateOf("") }
    ForgetLoginNavHost(
        navController = rememberNavController(),
        usernameState = usernameState
    )
}