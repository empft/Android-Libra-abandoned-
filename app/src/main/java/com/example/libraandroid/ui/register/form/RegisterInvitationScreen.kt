package com.example.libraandroid.ui.register.form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R
import com.example.libraandroid.ui.TextFieldError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

@Composable
fun RegisterInvitationScreen(
    invitationEmailState: MutableState<String>,
    invitationCodeState: MutableState<String>,
    countdown: State<String?>,
    onClickRequestCode: (email: String) -> Unit,
    onClick: (invitationEmail: String, invitationCode: String) -> Unit,
    modifier: Modifier = Modifier,
    invitationEmailError: String? = null,
    invitationCodeError: String? = null
) {
    val requestCodeEnabled =
        invitationEmailState.value.isNotEmpty() && (countdown.value == null)

    val nextEnabled = invitationEmailState.value.isNotEmpty() && invitationCodeState.value.isNotEmpty()
    val focusManager = LocalFocusManager.current
    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.form_vertical_spacing)
        )
    ) {
        Column {
            Text(
                stringResource(R.string.scr_regform__text__invitation_email),
                modifier = Modifier.align(
                    Alignment.Start
                )
            )

            OutlinedTextField(value = invitationEmailState.value, onValueChange = invitationEmailState.component2(), singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = invitationEmailError != null ,
                label = {
                    Text(
                        stringResource(R.string.g__textfield__email_address)
                    )
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            TextFieldError(message = invitationEmailError)
        }

        Row(modifier = Modifier.align(
            Alignment.End
        ), verticalAlignment = Alignment.CenterVertically) {
            countdown.value?.let {
                Text(it)
            }

            Spacer(modifier = Modifier.width(7.dp))
            OutlinedButton(onClick = {
                onClickRequestCode(invitationEmailState.value)
            }, enabled = requestCodeEnabled) {
                Text(stringResource(R.string.scr_regform__btn__request_invitation_code))
            }
        }

        Column {
            Text(stringResource(R.string.scr_regform__text__invitation_code))
            OutlinedTextField(value = invitationCodeState.value, onValueChange = invitationCodeState.component2(),
                modifier = Modifier.fillMaxWidth(0.5f) ,
                isError = invitationCodeError != null ,
                singleLine = true,
                label = {
                    Text(
                        stringResource(R.string.g__textfield__code)
                    )
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ), keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    if (nextEnabled) {
                        onClick(
                            invitationEmailState.value, invitationCodeState.value
                        )
                    }
                })
            )
            TextFieldError(message = invitationCodeError)
        }

        OutlinedButton(onClick = {
            focusManager.clearFocus()
            onClick(
                invitationEmailState.value, invitationCodeState.value
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
fun PreviewRegisterInvitationScreen() {
    RegisterInvitationScreen(
        remember { mutableStateOf("") },
        remember { mutableStateOf("") },
        flow {
            emit("10")
            delay(2000)
            emit("3")
            delay(2000)
            emit(null)
            delay(1000)
            emit("test")
        }.collectAsState(initial = "00:00"),
        onClickRequestCode = {

        },
        onClick = { _, _ ->

        }
    )
}
