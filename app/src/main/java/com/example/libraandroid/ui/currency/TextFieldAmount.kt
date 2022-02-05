package com.example.libraandroid.ui.currency

import android.icu.text.DecimalFormatSymbols
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.math.BigInteger
import java.util.*

@Composable
fun OutlinedTextFieldAmount(
    value: BigInteger,
    onValueChange: (BigInteger) -> Unit,
    decimalPlaces: Int,
    label: (@Composable () -> Unit)? = null,
    locale: Locale = LocalContext.current.resources.configuration.locales[0]
) {
    var hasDecimal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = formatAmount(
            value = value,
            decimalPlaces = decimalPlaces,
            alwaysShowDecimalAtEnd = hasDecimal
        ),
        onValueChange = { newValue ->
            parseAmount(
                amount = newValue,
                decimalPlaces = decimalPlaces,
                locale
            )?.let {
                hasDecimal = newValue.endsWith(DecimalFormatSymbols.getInstance(locale).decimalSeparator)
                onValueChange(it)
            }
        },
        label = label
    )
}

@Composable
fun TextFieldAmount(
    value: BigInteger,
    onValueChange: (BigInteger) -> Unit,
    decimalPlaces: Int,
    label: (@Composable () -> Unit)? = null,
    locale: Locale = LocalContext.current.resources.configuration.locales[0]
) {
    var hasDecimal by remember { mutableStateOf(false) }

    TextField(
        value = formatAmount(
            value = value,
            decimalPlaces = decimalPlaces,
            alwaysShowDecimalAtEnd = hasDecimal
        ),
        onValueChange = { newValue ->
            parseAmount(
                amount = newValue,
                decimalPlaces = decimalPlaces,
                locale
            )?.let {
                hasDecimal = newValue.endsWith(DecimalFormatSymbols.getInstance(locale).decimalSeparator)
                onValueChange(it)
            }
        },
        label = label
    )
}
