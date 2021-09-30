package com.example.libraandroid.ui.register.form

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import org.w3c.dom.Text

@Composable
fun RegisterInvitationScreen(
    invitationEmailState: MutableState<String>,
    invitationCodeState: MutableState<String>,
    countdown: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.form_vertical_spacing)
        )
    ) {
        Text(
            stringResource(R.string.scr_regform__text__invitation_email),
            modifier = Modifier.align(
                Alignment.Start
            )
        )

        OutlinedTextField(value = invitationEmailState.value, onValueChange = invitationEmailState.component2(), singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    stringResource(R.string.g__textfield__email_address)
                )
            },

        )

        Row {
            countdown?.let {
                it.
            }
            OutlinedButton(onClick = {

            }) {
                Text(stringResource(R.string.scr_regform__btn__request_invitation_code))
            }
        }


        Text(stringResource(R.string.scr_regform__text__invitation_code))
        OutlinedTextField(value = invitationCodeState.value, onValueChange = invitationCodeState.component2(),
            modifier = Modifier.fillMaxWidth(0.4f),
            singleLine = true,
            label = {
                Text(
                    stringResource(R.string.g__textfield__code)
                )
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
        )
        

        OutlinedButton(onClick = {

        }, modifier = Modifier.align(
            Alignment.End
        )) {
            Text(stringResource(R.string.g__btn__next))
        }

    }

}

@Preview
@Composable
fun PreviewRegisterInvitationScreen() {
    RegisterInvitationScreen(
        remember { mutableStateOf("") },
        remember { mutableStateOf("") }
    )
}
