package com.example.libraandroid.ui.register.form

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R

@Composable
fun RegisterLoadingScreen(

) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.scr_regform__text__loading),
            style = MaterialTheme.typography.h6
        )
        Spacer(Modifier.height(
            dimensionResource(R.dimen.g__form__vertical_spacing_large)
        ))
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(0.3f)
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewRegisterLoadingScreen() {
    RegisterLoadingScreen()
}
