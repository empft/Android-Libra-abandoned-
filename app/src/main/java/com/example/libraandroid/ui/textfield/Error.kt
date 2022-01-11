package com.example.libraandroid.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldError(message: String?) {
    message?.let {
        Text(
            text = it,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}