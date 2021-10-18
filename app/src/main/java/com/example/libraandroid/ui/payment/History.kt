package com.example.libraandroid.ui.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorModel
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

@Composable
fun PaymentHistoryRow(
    image: @Composable () -> Unit,
    title: String,
    amount: String,
    modifier: Modifier = Modifier,
    showDivider: Boolean = false,
    subtitle: String? = null
) {
    val imageSize = 48.dp
    val textStartPadding = 8.dp

    val rowStartPadding = 16.dp
    val rowEndPadding = 16.dp
    val rowTopPadding = 10.dp
    val rowBottomPadding = 10.dp

    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = rowStartPadding,
                    end = rowEndPadding,
                    top = rowTopPadding,
                    bottom = rowBottomPadding
                )
        ) {
            Box(modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(imageSize)
                .clip(RoundedCornerShape(10))
            ) {
                image()
            }

            Row(
                modifier = Modifier
                    .padding(start = textStartPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier
                    .weight(1f)
                ) {
                    Text(
                        title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style =  MaterialTheme.typography.subtitle1
                    )
                    subtitle?.let {
                        Text(
                            it,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.body2.copy(
                                color = MaterialTheme.typography.body2.color.copy(alpha = 0.6f)
                            )
                        )
                    }
                }

                Text(
                    amount,
                    maxLines = 1,
                    style =  MaterialTheme.typography.subtitle1
                )
            }
        }
        if (showDivider) {
            Divider()
        }
    }
}

@Preview
@Composable
fun PreviewPaymentHistoryRow() {
    PaymentHistoryRow({
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.scr_payhistory__image__avatar)
            )
        },
        "The Dessert Shop",
        "-$10.00",
        subtitle = "food & beverages"
    )
}

@Composable
fun PaymentHistory(
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item {
            PaymentHistoryRow({
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                )
            },
                "Title",
                "-$20.00",
                subtitle = "test",
                showDivider = true,
                modifier = Modifier.clickable {

                }
            )
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
            PaymentHistoryRow({
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = stringResource(R.string.scr_payhistory__image__avatar)
                    )
                },
                "Title",
                "-$20.00",
                subtitle = "test",
                showDivider = true,
                modifier = Modifier.clickable {

                }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Test() {
    ListItem(
        trailing = {
            Text("#$$000")
        },
        text = {
            Text(text = "A verklfksefeajfnknajkcanckAckcnacnackdaca")
        }
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewTest() {
    Test()
}