package com.example.libraandroid.ui.forgetlogin

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.theme.VanillaTheme

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    onClick: (token: String, password: String) -> Unit
) {
    val (token, setToken) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val buttonEnabled = token.isNotEmpty() && password.isNotEmpty()

    val focusManager = LocalFocusManager.current
    val clipboardManager = LocalClipboardManager.current
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
        OutlinedTextField(value = token, onValueChange = setToken, singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    clipboardManager.getText()?.let {
                        setToken(it.text)
                    }
                }) {
                    Icon(
                        imageVector  = Icons.Filled.ContentPaste,
                        stringResource(R.string.g__icon__paste)
                    )
                }
            },
            label = {
                Text(
                    stringResource(R.string.scr_forget__textfield__recovery_token)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
        OutlinedTextField(value = password, onValueChange = setPassword, singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    stringResource(R.string.scr_forget__textfield__new_password)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                if (buttonEnabled) {
                    onClick(token, password)
                }
            })
        )
        Spacer(modifier = Modifier.height(
            dimensionResource(R.dimen.g__form__vertical_spacing_large)
        ))
        OutlinedButton(onClick = {
            onClick(token, password)
        },
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonEnabled
        ) {
            Text(stringResource(R.string.scr_forget__btn__reset_password))
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewResetPasswordScreen() {
    VanillaTheme {
        ResetPasswordScreen { _, _ ->

        }
    }
}