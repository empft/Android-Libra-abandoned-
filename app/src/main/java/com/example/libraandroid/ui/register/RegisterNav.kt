package com.example.libraandroid.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.libraandroid.ui.forgetlogin.ForgetLoginNavHost
import com.example.libraandroid.ui.login.LoginNav
import com.example.libraandroid.ui.login.LoginResult
import com.example.libraandroid.ui.login.LoginScreen
import com.example.libraandroid.ui.register.form.RegisterInvitationScreen
import kotlinx.coroutines.flow.flow

enum class RegisterNav {
    Invitation,
    Names,
    Password,
    Email
}

@Composable
fun RegisterNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = RegisterNav.Names.name,
    ) {
        composable(RegisterNav.Names.name) {

        }

        composable(RegisterNav.Password.name) {

        }

        composable(RegisterNav.Email.name) {

        }
    }
}

@Composable
fun RegisterInvitationNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = RegisterNav.Invitation.name,
    ) {
        composable(RegisterNav.Invitation.name) {

        }

        composable(RegisterNav.Names.name) {

        }

        composable(RegisterNav.Password.name) {

        }

        composable(RegisterNav.Email.name) {

        }
    }
}


@Preview
@Composable
fun PreviewRegisterNavHost() {
    RegisterNavHost()
}