package com.example.libraandroid.ui.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

@Composable
fun PaymentScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
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
            onClickNfc = {}
        )

        ExpandableSurface(title =
            stringResource(R.string.scr_pay__text__history
        ), onExpand = { /*TODO*/

        }, modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
        )) {
            Column {
                PaymentHistoryRow(
                    image = { /*TODO*/ },
                    title = "",
                    amount = ""
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewPaymentScreen() {
    PaymentScreen()
}