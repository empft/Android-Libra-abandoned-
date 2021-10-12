package com.example.libraandroid.ui.forgetlogin

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.material.Icon
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.theme.VanillaTheme

@Composable
fun ForgetChoiceScreen(
    onClickForgetUsername: () -> Unit,
    onClickForgetPassword: () -> Unit,
    onClickResetPassword: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        ForgetChoiceButton(text = stringResource(R.string.g__btn__forget_username)) {
            onClickForgetUsername()
        }
        Divider()
        ForgetChoiceButton(text = stringResource(R.string.g__btn__forget_password)) {
            onClickForgetPassword()
        }
        Divider()
        ForgetChoiceButton(text = stringResource(R.string.g__btn__reset_password)) {
            onClickResetPassword()
        }
        Divider()
    }
}

@Composable
private fun ForgetChoiceButton(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(vertical = dimensionResource(R.dimen.g__list__vertical_padding)),
                style = MaterialTheme.typography.body1
            )
            Icon(
                Icons.Outlined.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewForgetChoiceScreen() {
    VanillaTheme {
        ForgetChoiceScreen(
            {}, {}, {}
        )
    }
}
