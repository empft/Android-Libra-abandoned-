package com.example.libraandroid.ui.forgetlogin

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.theme.VanillaTheme
import com.google.android.play.core.internal.q

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    onClick: (token: String, password: String) -> Unit
) {
    val (token, setToken) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(
            dimensionResource(R.dimen.form_vertical_spacing)
        ))
        OutlinedTextField(value = token, onValueChange = setToken, singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    stringResource(R.string.scr_forget__textfield__recovery_token)
                )
            }
        )
        OutlinedTextField(value = password, onValueChange = setPassword, singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    stringResource(R.string.scr_forget__textfield__new_password)
                )
            }
        )
        Spacer(modifier = Modifier.height(
            dimensionResource(R.dimen.form_vertical_spacing_large)
        ))
        OutlinedButton(onClick = {
            onClick(token, password)
        },
            modifier = Modifier.fillMaxWidth()
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