package com.example.libraandroid.ui.payprocess

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import java.math.BigInteger

data class CeloGatewayState(
    val recipients: List<String>,
    val currencies: List<String>,
    var selectedRecipient: Int?,
    var selectedCurrency: Int?,
    var fee: BigInteger,
    val decimalPlaces: Int
) {
    fun onSelectRecipient(index: Int) {
        selectedRecipient = index
    }

    fun onSelectCurrency(index: Int) {
        selectedCurrency = index
    }

    fun onEditFee(value: BigInteger) {
        fee = value
    }
}

@Composable
internal fun rememberCeloGatewayState(
    recipients: List<String>,
    currencies: List<String>,
    selectedRecipient: Int?,
    selectedCurrency: Int?,
    fee: BigInteger,
    decimalPlaces: Int
): CeloGatewayState {
    return remember {
        CeloGatewayState(
            recipients, currencies, selectedRecipient, selectedCurrency, fee, decimalPlaces
        )
    }
}