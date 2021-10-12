package com.example.libraandroid.ui.register

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.libraandroid.R
import com.example.libraandroid.service.network.StatelessClient
import com.example.libraandroid.ui.register.form.*
import com.example.libraandroid.ui.stringresource.stringResourceNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

enum class RegisterNav {
    Invitation,
    Names,
    Password,
    Email,
    Loading
}

@Composable
fun RegisterNavHost(
    onRegisterSuccess: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
) {
    val registerViewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(
            StatelessClient.registrationService
        )
    )

    val formModifier = Modifier.padding(
        horizontal = dimensionResource(R.dimen.g__form__horizontal_margin)
    )

    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = RegisterNav.Names.name,
    ) {

        coroutineScope.launch {
            registerViewModel.userError.collect {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = it
                )
            }
        }

        coroutineScope.launch {
            registerViewModel.registerResult.collect {
                if (it) {
                    onRegisterSuccess()
                } else {
                    if (
                        registerViewModel.usernameError.value != null ||
                        registerViewModel.displayNameError.value != null
                    ) {
                        navController.navigate(RegisterNav.Names.name)
                    } else if (registerViewModel.passwordError.value != null) {
                        navController.navigate(RegisterNav.Password.name)
                    } else if (registerViewModel.emailError.value != null) {
                        navController.navigate(RegisterNav.Email.name)
                    } else {
                        navController.navigate(RegisterNav.Names.name)
                    }
                }
            }
        }

        composable(RegisterNav.Names.name) {
            RegisterNamesScreen(
                usernameState = remember { mutableStateOf(
                    registerViewModel.registrationForm.username
                ) },
                displayNameState = remember { mutableStateOf(
                    registerViewModel.registrationForm.displayName
                ) },
                onClick = { username, displayName ->
                    if (registerViewModel.checkAndSetName(username, displayName)) {
                        navController.navigate(RegisterNav.Password.name)
                    }
                },
                modifier = formModifier,
                usernameError = stringResourceNull(registerViewModel.usernameError.value),
                displayNameError = stringResourceNull(registerViewModel.displayNameError.value)
            )
        }

        composable(RegisterNav.Password.name) {
            val context = LocalContext.current
            RegisterPasswordScreen(
                passwordState = remember { mutableStateOf(
                    registerViewModel.registrationForm.password
                ) },
                passwordResult = registerViewModel.passwordResult,
                onClick = {
                    registerViewModel.checkAndSetPassword(context, it)
                },
                onPasswordSuccess = {
                    navController.navigate(RegisterNav.Email.name)
                },
                modifier = formModifier,
                passwordError = stringResourceNull(registerViewModel.passwordError.value)
            )
        }

        composable(RegisterNav.Email.name) {
            RegisterEmailScreen(
                emailState = remember { mutableStateOf(
                    registerViewModel.registrationForm.email
                ) },
                onClick = {
                    if (registerViewModel.checkAndSetEmail(it)) {
                        registerViewModel.submitForm()
                        navController.navigate(RegisterNav.Loading.name)
                    }
                },
                modifier = formModifier,
                emailError = stringResourceNull(registerViewModel.emailError.value)
            )
        }

        composable(RegisterNav.Loading.name) {
            RegisterLoadingScreen()
        }
    }
}

@Composable
fun RegisterInvitationNavHost(
    onRegisterSuccess: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
) {
    val registerViewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(
            StatelessClient.registrationService
        )
    )

    val coroutineScope = rememberCoroutineScope()

    val formModifier = Modifier.padding(
        horizontal = dimensionResource(R.dimen.g__form__horizontal_margin)
    )
    
    NavHost(
        navController = navController,
        startDestination = RegisterNav.Invitation.name,
    ) {

        coroutineScope.launch {
            registerViewModel.userError.collect {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = it
                )
            }
        }

        coroutineScope.launch {
            registerViewModel.registerResult.collect {
                if (it) {
                    onRegisterSuccess()
                } else {
                    if (
                        registerViewModel.invitationEmailError.value != null ||
                        registerViewModel.invitationCodeError.value != null
                    ) {
                        navController.navigate(RegisterNav.Invitation.name)
                    } else if (
                        registerViewModel.usernameError.value != null ||
                        registerViewModel.displayNameError.value != null
                    ) {
                        navController.navigate(RegisterNav.Names.name)
                    } else if (registerViewModel.passwordError.value != null) {
                        navController.navigate(RegisterNav.Password.name)
                    } else if (registerViewModel.emailError.value != null) {
                        navController.navigate(RegisterNav.Email.name)
                    } else {
                        navController.navigate(RegisterNav.Invitation.name)
                    }
                }
            }
        }

        composable(RegisterNav.Invitation.name) {
            RegisterInvitationScreen(
                invitationEmailState = remember { mutableStateOf(
                    registerViewModel.registrationFormWithInvitation.invitationEmail
                ) },
                invitationCodeState = remember { mutableStateOf(
                    registerViewModel.registrationFormWithInvitation.invitationEmail
                ) },
                countdown = registerViewModel.countdown,
                onClickRequestCode = {
                    registerViewModel.requestInvitationCode(it)
                },
                onClick = { email, code ->
                    if (registerViewModel.checkAndSetInvitation(email, code)) {
                        navController.navigate(RegisterNav.Names.name)
                    }
                },
                modifier = formModifier,
                invitationEmailError = stringResourceNull(registerViewModel.invitationEmailError.value),
                invitationCodeError = stringResourceNull(registerViewModel.invitationCodeError.value)
            )
        }

        composable(RegisterNav.Names.name) {
            RegisterNamesScreen(
                usernameState = remember { mutableStateOf(
                    registerViewModel.registrationFormWithInvitation.username
                ) },
                displayNameState = remember { mutableStateOf(
                    registerViewModel.registrationFormWithInvitation.displayName
                ) },
                onClick = { username, displayName ->
                    if (registerViewModel.checkAndSetName(username, displayName)) {
                        navController.navigate(RegisterNav.Password.name)
                    }
                },
                modifier = formModifier,
                usernameError = stringResourceNull(registerViewModel.usernameError.value),
                displayNameError = stringResourceNull(registerViewModel.displayNameError.value)
            )
        }

        composable(RegisterNav.Password.name) {
            val context = LocalContext.current
            RegisterPasswordScreen(
                passwordState = remember { mutableStateOf(
                    registerViewModel.registrationFormWithInvitation.password
                ) },
                passwordResult = registerViewModel.passwordResult,
                onClick = {
                    registerViewModel.checkAndSetPassword(context, it)
                },
                onPasswordSuccess = {
                    navController.navigate(RegisterNav.Email.name)
                },
                modifier = formModifier,
                passwordError = stringResourceNull(registerViewModel.passwordError.value)
            )
        }

        composable(RegisterNav.Email.name) {
            RegisterEmailScreen(
                emailState = remember { mutableStateOf(
                    registerViewModel.registrationFormWithInvitation.email
                ) },
                onClick = {
                    if (registerViewModel.checkAndSetEmail(it)) {
                        registerViewModel.submitFormWithInvitation()
                        navController.navigate(RegisterNav.Loading.name)
                    }
                },
                modifier = formModifier,
                emailError = stringResourceNull(registerViewModel.emailError.value)
            )
        }

        composable(RegisterNav.Loading.name) {
            RegisterLoadingScreen()
        }
    }
}


@Preview
@Composable
fun PreviewRegisterNavHost() {
    val navController = rememberNavController()
    RegisterNavHost(
        {},
        navController = navController
    )
}