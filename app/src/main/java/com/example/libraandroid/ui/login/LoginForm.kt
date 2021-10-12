package com.example.libraandroid.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
        var passwordVisibility: Boolean by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            trailingIcon = {
                val image = if (passwordVisibility)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector  = image, stringResource(R.string.g__icon__visibility))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.g__textfield__password)) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (buttonEnabled) {
                    focusManager.clearFocus()
                    onClick(username, password)
                }
            })
        )
        Spacer(Modifier.size(dimensionResource(R.dimen.g__form__vertical_spacing)))
        OutlinedButton(
            onClick = {
                focusManager.clearFocus()
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

