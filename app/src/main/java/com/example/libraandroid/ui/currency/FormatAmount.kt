package com.example.libraandroid.ui.currency

import android.icu.text.DecimalFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.text.BidiFormatter
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.util.*
import kotlin.math.absoluteValue

private fun numberToString(
    value: BigInteger,
    decimalPlaces: Int,
    currencyCode: String,
    sign: String,
    numberOfDigits: Int?,
    locale: Locale
): String {
    val number = value
        .toBigDecimal(mathContext = MathContext(value.toString().length))
        .divide(BigDecimal.TEN.pow(decimalPlaces),decimalPlaces, RoundingMode.UNNECESSARY)
        .stripTrailingZeros()

    val scale = number.scale()
    val precision = number.precision()
    val isRtl = BidiFormatter.getInstance(locale).isRtlContext

    val currencyFormatter = DecimalFormat.getCurrencyInstance(locale)
    val isSymbolAtStart = currencyFormatter
        .format(0)
        .startsWith(currencyFormatter.currency.symbol)

    val formatter = if (numberOfDigits != null) {
        // makes the value show less digits than number of digits (excluding 0.)
        if ((precision - scale).absoluteValue > numberOfDigits) {
            (DecimalFormat.getScientificInstance(locale) as DecimalFormat).apply {
                maximumSignificantDigits = numberOfDigits
            }
        } else {
            (DecimalFormat.getInstance(locale) as DecimalFormat).apply {
                if (number.abs() > BigDecimal.ZERO && number.abs() < BigDecimal.ONE) {
                    maximumFractionDigits = numberOfDigits
                    minimumFractionDigits = numberOfDigits
                } else {
                    maximumSignificantDigits = numberOfDigits
                }
            }
        }
    } else {
        DecimalFormat.getInstance(locale).apply {
            maximumFractionDigits = scale
            minimumFractionDigits = scale
        }
    }

    val formattedAmount = formatter.format(number)
    return if (isRtl) {
        if (isSymbolAtStart) "$formattedAmount $currencyCode$sign" else "$currencyCode $formattedAmount$sign"
    } else {
        if (isSymbolAtStart) "$sign$currencyCode $formattedAmount" else "$sign$formattedAmount $currencyCode"
    }
}

@Composable
fun formatAmount(
    value: BigInteger,
    decimalPlaces: Int,
    currencyCode: String = "",
    sign: String = "",
    //number of digits shown (excluding zero integer for 0<|x|<1)
    numberOfDigits: Int? = null,
    locale: Locale = LocalContext.current.resources.configuration.locales[0]
): String {
    return numberToString(
        value = value, decimalPlaces = decimalPlaces,
        currencyCode = currencyCode, sign = sign, numberOfDigits = numberOfDigits,
        locale = locale
    )
}

@Composable
fun formatAmount(
    value: ULong,
    decimalPlaces: Int,
    currencyCode: String = "",
    sign: String = "",
    //number of digits shown (excluding zero integer for 0<|x|<1)
    numberOfDigits: Int? = null,
    locale: Locale = LocalContext.current.resources.configuration.locales[0]
): String {
    return numberToString(
        value = BigInteger(value.toString()), decimalPlaces = decimalPlaces,
        currencyCode = currencyCode, sign = sign, numberOfDigits = numberOfDigits,
        locale = locale
    )
}