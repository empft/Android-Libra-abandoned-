package com.example.libraandroid.ui.register.form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.TextFieldError

@Composable
fun RegisterNamesScreen(
    usernameState: MutableState<String>,
    displayNameState: MutableState<String>,
    onClick: (username: String, displayName: String) -> Unit,
    modifier: Modifier = Modifier,
    usernameError: String? = null,
    displayNameError: String? = null
) {
    val nextEnabled = usernameState.value.isNotEmpty() && displayNameState.value.isNotEmpty()
    val focusManager = LocalFocusManager.current
    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.form_vertical_spacing)
        )
    ) {
        Column {
            Text(
                stringResource(R.string.scr_regform__text__name),
                modifier = Modifier.align(
                    Alignment.Start
                )
            )

            OutlinedTextField(value = usernameState.value, onValueChange = usernameState.component2(), singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = usernameError != null,
                label = {
                    Text(
                        stringResource(R.string.g__textfield__username)
                    )
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ), keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            TextFieldError(message = usernameError)

        }
        OutlinedTextField(value = displayNameState.value, onValueChange = displayNameState.component2(),
            modifier = Modifier.fillMaxWidth(),
            isError = displayNameError != null,
            singleLine = true,
            label = {
                Text(
                    stringResource(R.string.g__textfield__display_name)
                )
            }, keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ), keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                if (nextEnabled) {
                    onClick(
                        usernameState.value, displayNameState.value
                    )
                }
            })
        )
        TextFieldError(message = displayNameError)


        OutlinedButton(onClick = {
            onClick(
                usernameState.value, displayNameState.value
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
fun PreviewRegisterNamesScreen() {
    RegisterNamesScreen(
        remember { mutableStateOf("") },
        remember { mutableStateOf("") },
        onClick = { _, _ ->

        }
    )
}