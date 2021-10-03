package com.example.libraandroid.ui.register.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.TextFieldError

@Composable
fun RegisterPasswordScreen(
    passwordState: MutableState<String>,
    onClick: (password: String) -> Unit,
    modifier: Modifier = Modifier,
    passwordError: String? = null
) {
    val nextEnabled = passwordState.value.isNotEmpty()
    val focusManager = LocalFocusManager.current
    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.form_vertical_spacing)
        )
    ) {

        Column {
            Text(
                stringResource(R.string.scr_regform__text__password),
                modifier = Modifier.align(
                    Alignment.Start
                )
            )

            OutlinedTextField(value = passwordState.value, onValueChange = passwordState.component2(), singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError != null,
                label = {
                    Text(
                        stringResource(R.string.g__textfield__password)
                    )
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    if (nextEnabled) {
                        onClick(
                            passwordState.value
                        )
                    }
                })
            )
            TextFieldError(message = passwordError)
        }

        OutlinedButton(onClick = {
            onClick(
                passwordState.value
            )
        }, modifier = Modifier.align(
            Alignment.End
        ), enabled = nextEnabled
        ) {
            Text(stringResource(R.string.g__btn__next))
        }
    }
}

@Preview
@Composable
fun PreviewRegisterPasswordScreen() {
    RegisterPasswordScreen(
        remember { mutableStateOf("") },
        onClick = {}
    )
}