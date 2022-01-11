package com.example.libraandroid.ui.textfield

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import com.example.libraandroid.R

@Composable
fun PasteIconButton(
    onPaste: (String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    IconButton(onClick = {
        clipboardManager.getText()?.let {
            onPaste(it.text)
        }
    }) {
        Icon(
            imageVector  = Icons.Filled.ContentPaste,
            stringResource(R.string.g__icon__paste)
        )
    }
}