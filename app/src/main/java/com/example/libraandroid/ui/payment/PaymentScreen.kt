package com.example.libraandroid.ui.payment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R
import com.example.libraandroid.ui.currency.formatAmount
import com.example.libraandroid.ui.transactionhistory.Transaction
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun PaymentScreen(
    transactions: List<Transaction>
) {
    val verticalSpace = 20.dp
    Column(
        verticalArrangement = Arrangement.spacedBy(verticalSpace)
    ) {
        Balance(
            listOf("$20.00", "30$30", "c$100"),{}, {},
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
        )

        Pay(
            onClickQr = {},
            onClickDirect = {},
            onClickNfc = {},
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        ExpandableSurface(title =
            stringResource(R.string.scr_pay__text__history
        ), onExpand = { /*TODO*/

        }, modifier = Modifier
            .padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )) {

        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewPaymentScreen() {
    PaymentScreen(
        transactions = listOf()
    )
}