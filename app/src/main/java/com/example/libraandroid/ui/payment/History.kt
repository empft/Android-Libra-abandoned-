package com.example.libraandroid.ui.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorModel
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

@Composable
fun PaymentHistoryRow(
    image: Painter,
    title: String,
    amount: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    val imageSize = 50.dp
    val textStartPadding = 8.dp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = image,
            contentDescription = stringResource(R.string.scr_payhistory__image__avatar),
            modifier = Modifier
                .size(imageSize)
        )

        Row(
            modifier = Modifier
                .padding(start = textStartPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                subtitle?.let {
                    Text(
                        it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.caption.copy(
                            color = MaterialTheme.typography.caption.color.copy(alpha = 0.6f)
                        )
                    )
                }
            }

            Text(
                amount,
                maxLines = 1,
            )
        }
    }
}

@Preview
@Composable
fun PreviewPaymentHistoryRow() {
    PaymentHistoryRow(
        painterResource(R.drawable.ic_launcher_foreground),
        "The Dessert Shop3333333333333366333",
        "-$10.00",
        subtitle = "food & beverages"
    )
}

@Composable
fun PaymentHistory() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        LazyColumn {

        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewPaymentHistory() {
    val myList = listOf(1,2,3,4,5)

    LazyColumn {
        items(5) {
            PaymentHistoryRow(
                painterResource(R.drawable.ic_launcher_foreground),
                "Title",
                "-$20.00",
                subtitle = "test",
                modifier = Modifier.clickable {

                }
            )
            Divider(thickness = 2.dp)
        }
    }
}