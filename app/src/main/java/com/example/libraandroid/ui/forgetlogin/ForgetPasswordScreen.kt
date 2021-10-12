package com.example.libraandroid.ui.forgetlogin

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.theme.VanillaTheme

@Composable
fun ForgetPasswordScreen(
    emailState: MutableState<String>,
    usernameState: MutableState<String>,
    modifier: Modifier = Modifier,
    onClick: (email: String, username: String) -> Unit
) {
    val buttonEnabled = emailState.value.isNotEmpty() && usernameState.value.isNotEmpty()

    val focusManager = LocalFocusManager.current
    Column(modifier = modifier
        .fillMaxHeight()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(
            dimensionResource(R.dimen.g__form__vertical_spacing)
        ))
        OutlinedTextField(value = emailState.value, onValueChange = emailState.component2(), singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    stringResource(R.string.g__textfield__email_address)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
        OutlinedTextField(value = usernameState.value, onValueChange = usernameState.component2(), singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    stringResource(R.string.g__textfield__username)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                if (buttonEnabled) {
                    onClick(emailState.value, usernameState.value)
                }
            })
        )
        Spacer(modifier = Modifier.height(
            dimensionResource(R.dimen.g__form__vertical_spacing_large)
        ))
        OutlinedButton(onClick = {
            onClick(emailState.value, usernameState.value)
        },
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonEnabled
        ) {
            Text(stringResource(R.string.scr_forget__btn__forget_password))
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewForgetPasswordScreen() {
    val emailState = remember { mutableStateOf("") }
    val usernameState = remember { mutableStateOf("") }
    VanillaTheme {
        ForgetPasswordScreen(
            emailState = emailState,
            usernameState = usernameState
        ) { _, _ ->

        }
    }
}