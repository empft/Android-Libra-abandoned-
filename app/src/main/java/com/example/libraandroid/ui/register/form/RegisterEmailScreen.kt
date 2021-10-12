package com.example.libraandroid.ui.register.form

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.TextFieldError

@Composable
fun RegisterEmailScreen(
    emailState: MutableState<String>,
    onClick: (email: String) -> Unit,
    modifier: Modifier = Modifier,
    emailError: String? = null
) {
    val doneEnabled = emailState.value.isNotEmpty()
    val focusManager = LocalFocusManager.current
    Column(modifier = modifier
        .fillMaxHeight()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        },
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.g__form__vertical_spacing)
        )
    ) {

        Column {
            Text(
                stringResource(R.string.scr_regform__text__recovery_email),
                modifier = Modifier.align(
                    Alignment.Start
                )
            )

            OutlinedTextField(value = emailState.value, onValueChange = emailState.component2(), singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = emailError != null,
                label = {
                    Text(
                        stringResource(R.string.g__textfield__email_address)
                    )
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    if (doneEnabled) {
                        onClick(
                            emailState.value
                        )
                    }
                })
            )
            TextFieldError(message = emailError)
        }

        OutlinedButton(onClick = {
            onClick(
                emailState.value
            )
        }, modifier = Modifier.align(
            Alignment.End
        ), enabled = doneEnabled
        ) {
            Text(stringResource(R.string.g__btn__done))
        }
    }
}

@Preview
@Composable
fun PreviewRegisterEmailScreen() {
    RegisterEmailScreen(
        remember { mutableStateOf("") },
        onClick = {}
    )
}