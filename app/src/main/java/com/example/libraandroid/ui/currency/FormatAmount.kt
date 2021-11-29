package com.example.libraandroid.ui.currency

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import android.icu.text.NumberFormat
import android.icu.util.Currency
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.text.BidiFormatter
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.util.*

@Composable
private fun numberToString(
    value: BigInteger,
    decimalPlaces: Int,
    symbol: String = "",
    sign: String = ""
): String {
    val number = value
        .toBigDecimal(mathContext = MathContext(value.toString().length))
        .divide(BigDecimal.TEN.pow(decimalPlaces),decimalPlaces, RoundingMode.UNNECESSARY)
        .stripTrailingZeros()

    val scale = number.scale()

    val locale = LocalContext.current.resources.configuration.locales[0]
    val formatter = DecimalFormat.getCurrencyInstance(locale)
    formatter.minimumFractionDigits = scale
    formatter.maximumFractionDigits = scale

    val isRtl = BidiFormatter.getInstance(locale).isRtlContext
    val formattedAmount = formatter.format(number).replace(formatter.currency.symbol, symbol)
    return if (isRtl) "$formattedAmount$sign" else "$sign$formattedAmount"
}

@Composable
fun formatAmount(
    value: BigInteger,
    decimalPlaces: Int,
    symbol: String = "",
    sign: String = ""
): String {
    return numberToString(
        value = value, decimalPlaces = decimalPlaces,
        symbol = symbol, sign = sign
    )
}

@Composable
fun formatAmount(
    value: ULong,
    decimalPlaces: Int,
    symbol: String = "",
    sign: String = ""
): String {
    return numberToString(
        value = BigInteger(value.toString()), decimalPlaces = decimalPlaces,
        symbol = symbol, sign = sign
    )
}