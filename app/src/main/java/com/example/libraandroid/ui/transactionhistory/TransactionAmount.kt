package com.example.libraandroid.ui.currency

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.libraandroid.ui.theme.negativeColor
import com.example.libraandroid.ui.theme.positiveColor
import com.example.libraandroid.ui.transactionhistory.TransactionViewer
import java.math.BigInteger

interface TransferAmount {
    fun amount(): BigInteger
    fun decimalPlaces(): Int
    fun currency(): String
    fun viewer(): TransactionViewer
}

@Composable
fun TransferAmountUi(
    transfer: TransferAmount
) {
    val (color, sign) = when(transfer.viewer()) {
        TransactionViewer.Anonymous -> {
            Pair(Color.Unspecified, "")
        }
        TransactionViewer.Recipient -> {
            Pair(positiveColor(), "+")
        }
        TransactionViewer.Sender -> {
            Pair(negativeColor(), "")
        }
        TransactionViewer.Self -> {
            Pair(Color.LightGray, "=")
        }
    }
    Text(formatAmount(
        value = transfer.amount(), decimalPlaces = transfer.decimalPlaces(),
        currencyCode = transfer.currency(), sign
    ), color = color)
}