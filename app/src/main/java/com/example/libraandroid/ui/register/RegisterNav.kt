package com.example.libraandroid.ui.register

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.libraandroid.R
import com.example.libraandroid.domain.account.registration.AccountRegistrationInteractor
import com.example.libraandroid.network.StatelessClient
import com.example.libraandroid.ui.navigation.rememberParentEntry
import com.example.libraandroid.ui.register.form.*

enum class RegisterNav {
    Invitation,
    Names,
    Password,
    Email,
    Loading
}

fun NavGraphBuilder.registerNav(
    route: String,
    navController: NavHostController
) {
    @Composable
    fun formModifier(): Modifier {
        return Modifier.padding(
            horizontal = dimensionResource(R.dimen.g__form__horizontal_margin)
        )
    }

    @Composable
    fun registerViewModel(navBackStackEntry: NavBackStackEntry): RegisterViewModel {
        return viewModel(
            viewModelStoreOwner = navBackStackEntry.rememberParentEntry(navController),
            factory =  RegisterViewModelFactory(
                AccountRegistrationInteractor(
                    StatelessClient.registration
                )
            )
        )
    }

    navigation(startDestination = RegisterNav.Names.name, route = route) {
        composable(RegisterNav.Names.name) { navBackStackEntry ->
            val registerViewModel: RegisterViewModel = registerViewModel(navBackStackEntry)
            val scaffoldState = rememberScaffoldState()
            val context = LocalContext.current

            LaunchedEffect(scaffoldState.snackbarHostState) {
                registerViewModel.reqCodeError.collect {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                }
            }

            Scaffold(
                scaffoldState = scaffoldState
            ) {
                RegisterNamesScreen(
                    usernameState = remember { mutableStateOf(
                        registerViewModel.registrationForm.username
                    ) },
                    displayNameState = remember { mutableStateOf(
                        registerViewModel.registrationForm.displayName
                    ) },
                    onClick = { username, displayName ->
                        if (registerViewModel.checkAndSetName(context, username, displayName)) {
                            navController.navigate(RegisterNav.Password.name)
                        }
                    },
                    modifier = formModifier(),
                    usernameError = registerViewModel.usernameError.value,
                    displayNameError = registerViewModel.displayNameError.value
                )
            }
        }

        composable(RegisterNav.Password.name) { navBackStackEntry ->
            val registerViewModel: RegisterViewModel = registerViewModel(navBackStackEntry)
            val scaffoldState = rememberScaffoldState()

            LaunchedEffect(scaffoldState.snackbarHostState) {
                registerViewModel.reqCodeError.collect {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                }
            }

            val context = LocalContext.current
            Scaffold(scaffoldState = scaffoldState) {
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
                    modifier = formModifier(),
                    passwordError = registerViewModel.passwordError.value
                )
            }
        }

        composable(RegisterNav.Email.name) { navBackStackEntry ->
            val registerViewModel: RegisterViewModel = registerViewModel(navBackStackEntry)

            val scaffoldState = rememberScaffoldState()
            val context = LocalContext.current

            LaunchedEffect(scaffoldState.snackbarHostState) {
                registerViewModel.reqCodeError.collect {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                }
            }

            Scaffold(scaffoldState = scaffoldState) {
                RegisterEmailScreen(
                    emailState = remember { mutableStateOf(
                        registerViewModel.registrationForm.email
                    ) },
                    onClick = {
                        if (registerViewModel.checkAndSetEmail(context, it)) {
                            registerViewModel.submitForm()
                            navController.navigate(RegisterNav.Loading.name)
                        }
                    },
                    modifier = formModifier(),
                    emailError = registerViewModel.emailError.value
                )
            }
        }

        composable(RegisterNav.Loading.name) { navBackStackEntry ->
            val registerViewModel: RegisterViewModel = registerViewModel(navBackStackEntry)

            val scaffoldState = rememberScaffoldState()
            val context = LocalContext.current

            LaunchedEffect(scaffoldState.snackbarHostState) {
                registerViewModel.reqCodeError.collect {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                }
            }

            LaunchedEffect(Unit) {
                registerViewModel.registerFailure.collect {
                    when(it) {
                        RegisterFailure.Form -> {
                            if (
                                registerViewModel.usernameError.value != null ||
                                registerViewModel.displayNameError.value != null
                            ) {
                                navController.popBackStack(
                                    route = RegisterNav.Names.name,
                                    inclusive = false
                                )
                            } else if (registerViewModel.passwordError.value != null) {
                                navController.popBackStack(
                                    route = RegisterNav.Password.name,
                                    inclusive = false
                                )
                            } else if (registerViewModel.emailError.value != null) {
                                navController.popBackStack(
                                    route = RegisterNav.Email.name,
                                    inclusive = false
                                )
                            } else {
                                navController.popBackStack(
                                    route = RegisterNav.Names.name,
                                    inclusive = false
                                )
                            }
                        }
                        is RegisterFailure.Id -> {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = context.resources.getString(it.id)
                            )
                        }
                    }
                }
            }

            Scaffold(scaffoldState = scaffoldState) {
                RegisterLoadingScreen()
            }
        }
    }
}

fun NavGraphBuilder.registerInvitationNav(
    route: String,
    navController: NavHostController
) {
    @Composable
    fun formModifier(): Modifier {
        return Modifier.padding(
            horizontal = dimensionResource(R.dimen.g__form__horizontal_margin)
        )
    }

    @Composable
    fun registerViewModel(navBackStackEntry: NavBackStackEntry): RegisterViewModel {
        return viewModel(
            viewModelStoreOwner = navBackStackEntry.rememberParentEntry(navController),
            factory =  RegisterViewModelFactory(
                AccountRegistrationInteractor(
                    StatelessClient.registration
                )
            )
        )
    }

    navigation(startDestination = RegisterNav.Invitation.name, route = route) {
        composable(RegisterNav.Invitation.name) { navBackStackEntry ->
            val registerViewModel: RegisterViewModel = registerViewModel(navBackStackEntry)

            val scaffoldState = rememberScaffoldState()
            val context = LocalContext.current

            LaunchedEffect(scaffoldState.snackbarHostState) {
                registerViewModel.reqCodeError.collect {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                }
            }

            Scaffold(scaffoldState = scaffoldState) {
                RegisterInvitationScreen(
                    invitationEmailState = remember { mutableStateOf(
                        registerViewModel.registrationFormWithInvitation.invitationEmail
                    ) },
                    invitationCodeState = remember { mutableStateOf(
                        registerViewModel.registrationFormWithInvitation.invitationEmail
                    ) },
                    countdown = registerViewModel.countdown,
                    onClickRequestCode = {
                        registerViewModel.requestInvitationCode(context, it)
                    },
                    onClick = { email, code ->
                        if (registerViewModel.checkAndSetInvitation(context, email, code)) {
                            navController.navigate(RegisterNav.Names.name)
                        }
                    },
                    modifier = formModifier(),
                    invitationEmailError = registerViewModel.invitationEmailError.value,
                    invitationCodeError = registerViewModel.invitationCodeError.value
                )
            }
        }

        composable(RegisterNav.Names.name) { navBackStackEntry ->
            val registerViewModel: RegisterViewModel = registerViewModel(navBackStackEntry)

            val scaffoldState = rememberScaffoldState()
            val context = LocalContext.current

            LaunchedEffect(scaffoldState.snackbarHostState) {
                registerViewModel.reqCodeError.collect {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                }
            }

            Scaffold(scaffoldState = scaffoldState) {
                RegisterNamesScreen(
                    usernameState = remember { mutableStateOf(
                        registerViewModel.registrationFormWithInvitation.username
                    ) },
                    displayNameState = remember { mutableStateOf(
                        registerViewModel.registrationFormWithInvitation.displayName
                    ) },
                    onClick = { username, displayName ->
                        if (registerViewModel.checkAndSetName(context, username, displayName)) {
                            navController.navigate(RegisterNav.Password.name)
                        }
                    },
                    modifier = formModifier(),
                    usernameError = registerViewModel.usernameError.value,
                    displayNameError = registerViewModel.displayNameError.value
                )
            }
        }

        composable(RegisterNav.Password.name) { navBackStackEntry ->
            val registerViewModel: RegisterViewModel = registerViewModel(navBackStackEntry)

            val scaffoldState = rememberScaffoldState()
            val context = LocalContext.current

            LaunchedEffect(scaffoldState.snackbarHostState) {
                registerViewModel.reqCodeError.collect {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                }
            }

            Scaffold(scaffoldState = scaffoldState) {
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
                    modifier = formModifier(),
                    passwordError = registerViewModel.passwordError.value
                )
            }
        }

        composable(RegisterNav.Email.name) { navBackStackEntry ->
            val registerViewModel: RegisterViewModel = registerViewModel(navBackStackEntry)

            val context = LocalContext.current
            val scaffoldState = rememberScaffoldState()
            LaunchedEffect(scaffoldState.snackbarHostState) {
                registerViewModel.reqCodeError.collect {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                }
            }

            Scaffold(scaffoldState = scaffoldState) {
                RegisterEmailScreen(
                    emailState = remember { mutableStateOf(
                        registerViewModel.registrationFormWithInvitation.email
                    ) },
                    onClick = {
                        if (registerViewModel.checkAndSetEmail(context, it)) {
                            registerViewModel.submitFormWithInvitation()
                            navController.navigate(RegisterNav.Loading.name)
                        }
                    },
                    modifier = formModifier(),
                    emailError = registerViewModel.emailError.value
                )
            }
        }

        composable(RegisterNav.Loading.name) { navBackStackEntry ->
            val registerViewModel: RegisterViewModel = registerViewModel(navBackStackEntry)

            val context = LocalContext.current
            val scaffoldState = rememberScaffoldState()
            LaunchedEffect(scaffoldState.snackbarHostState) {
                registerViewModel.reqCodeError.collect {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it
                    )
                }
            }

            LaunchedEffect(Unit) {
                registerViewModel.registerFailure.collect {
                    when(it) {
                        RegisterFailure.Form -> {
                            if (
                                registerViewModel.invitationEmailError.value != null ||
                                registerViewModel.invitationCodeError.value != null
                            ) {
                                navController.popBackStack(
                                    route = RegisterNav.Invitation.name,
                                    inclusive = false
                                )
                            } else if (
                                registerViewModel.usernameError.value != null ||
                                registerViewModel.displayNameError.value != null
                            ) {
                                navController.popBackStack(
                                    route = RegisterNav.Names.name,
                                    inclusive = false
                                )
                            } else if (registerViewModel.passwordError.value != null) {
                                navController.popBackStack(
                                    route = RegisterNav.Password.name,
                                    inclusive = false
                                )
                            } else if (registerViewModel.emailError.value != null) {
                                navController.popBackStack(
                                    route = RegisterNav.Email.name,
                                    inclusive = false
                                )
                            } else {
                                navController.popBackStack(
                                    route = RegisterNav.Invitation.name,
                                    inclusive = false
                                )
                            }
                        }
                        is RegisterFailure.Id -> {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = context.resources.getString(it.id)
                            )
                        }
                    }
                }
            }

            Scaffold(scaffoldState = scaffoldState) {
                RegisterLoadingScreen()
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterInvitationNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start") {
        registerInvitationNav(
            "start",
            navController)
    }
}

@Preview
@Composable
fun PreviewRegisterNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start") {
        registerNav(
            "start",
            navController)
    }
}