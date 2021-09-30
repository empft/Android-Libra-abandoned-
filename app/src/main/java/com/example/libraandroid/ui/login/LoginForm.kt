package com.example.libraandroid.ui.login

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.theme.VanillaTheme

@Composable
fun LoginForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onClick: (username: String, password: String) -> Unit
) {
    val buttonEnabled = username.isNotEmpty() && password.isNotEmpty()

    val focusManager = LocalFocusManager.current
    Column {
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.g__textfield__username)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.g__textfield__password)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (buttonEnabled) {
                    onClick(username, password)
                }
            })
        )
        Spacer(Modifier.size(dimensionResource(R.dimen.form_vertical_spacing)))
        OutlinedButton(
            onClick = {
                onClick(username, password)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonEnabled
        ) {
            Text(
                text = stringResource(R.string.g__btn__sign_in)
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoginForm() {
    VanillaTheme(darkTheme = false) {
        val (username, setUsername) = remember { mutableStateOf("") }
        val (password, setPassword) = remember { mutableStateOf("") }
        LoginForm(
            username,
            setUsername,
            password,
            setPassword, { _, _ ->

            }
        )
    }
}

