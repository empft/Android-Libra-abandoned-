package com.example.libraandroid.ui.register.form

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R
import com.example.libraandroid.ui.TextFieldError
import com.example.libraandroid.ui.register.PasswordResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Composable
fun RegisterPasswordScreen(
    passwordState: MutableState<String>,
    passwordResult: Flow<PasswordResult>,
    onClick: (password: String) -> Unit,
    onPasswordSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    passwordError: String? = null
) {
    val (isLoading, setLoading) = remember { mutableStateOf(false) }

    val nextEnabled = passwordState.value.isNotEmpty() && !isLoading
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()

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

        coroutineScope.launch {
            passwordResult.collect {
                when(it) {
                    PasswordResult.Loading -> setLoading(true)
                    PasswordResult.Success -> {
                        onPasswordSuccess()
                    }
                    PasswordResult.Empty -> setLoading(false)
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            }

            Spacer(Modifier.width(8.dp))

            OutlinedButton(onClick = {
                onClick(
                    passwordState.value
                )
            }, enabled = nextEnabled
            ) {
                Text(stringResource(R.string.g__btn__next))
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterPasswordScreen() {
    RegisterPasswordScreen(
        remember { mutableStateOf("") },
        flow {
            emit(PasswordResult.Loading)
            kotlinx.coroutines.delay(10000L)
            emit(PasswordResult.Empty)
        },
        onClick = {},
        onPasswordSuccess = {}
    )
}