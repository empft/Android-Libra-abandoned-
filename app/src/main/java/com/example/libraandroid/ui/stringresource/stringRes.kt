package com.example.libraandroid.ui.stringresource

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

@Composable
@ReadOnlyComposable
fun stringResourceNull(@StringRes id: Int?): String? {
    id?.let {
        return stringResource(it)
    }
    return null
}