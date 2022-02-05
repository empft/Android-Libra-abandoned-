package com.example.libraandroid.ui.login

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R
import com.example.libraandroid.ui.theme.VanillaTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    usernameState: MutableState<String> = remember { mutableStateOf("") },
    passwordState: MutableState<String> = remember { mutableStateOf("") },
    loginState: Flow<LoginFailure>,
    onClickForget: () -> Unit,
    onClickRegister: () -> Unit,
    onClickLogin: (username: String, password: String) -> Unit,
    onClickGuestLogin: () -> Unit
) {
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current
    Column(modifier = modifier
        .fillMaxHeight()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }
        .padding(
            horizontal = dimensionResource(R.dimen.g__form__horizontal_margin),
            vertical = 8.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginForm(
            usernameState.value,
            usernameState.component2(),
            passwordState.value,
            passwordState.component2()
        ) { username, password ->
            onClickLogin(username, password)
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onClickForget) {
                    Text(
                        text = stringResource(R.string.scr_login__btn__forget_credentials)
                    )
                }
                TextButton(onClick = onClickRegister) {
                    Text(
                        text = stringResource(R.string.g__btn__sign_up)
                    )
                }
            }

            OutlinedButton(onClick = onClickGuestLogin, modifier = Modifier
                .fillMaxWidth()
            ) {
                Text(stringResource(R.string.g__btn__guest_sign_in))
            }
        }

        LaunchedEffect(scaffoldState.snackbarHostState) {
            loginState.collect {
                when(it) {
                    is LoginFailure.Text -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it.message
                        )
                    }
                    is LoginFailure.Id -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = context.getString(it.id)
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewLoginScreen() {

    VanillaTheme {
        LoginScreen(
            loginState = flow {

            },
            onClickForget = {},
            onClickRegister = {},
            onClickLogin = {_, _ ->},
            onClickGuestLogin = {}
        )
    }
}