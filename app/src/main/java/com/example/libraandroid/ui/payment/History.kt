package com.example.libraandroid.ui.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.libraandroid.R
import com.example.libraandroid.ui.currency.CurrencyConstant
import com.example.libraandroid.ui.currency.formatAmount
import com.example.libraandroid.ui.displayname.DisplayName
import com.example.libraandroid.ui.transactionhistory.AddressWithId
import com.example.libraandroid.ui.transactionhistory.Transaction
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.absoluteValue

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
                    DisplayName(
                        name = title
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
                contentDescription = null
            )
        },
        "The Dessert Shop",
        "-$10.00",
        subtitle = "food & beverages"
    )
}

private fun getRandomColors(address: String): List<Color> {
    val colourList = mutableListOf(
        Color.Blue,
        Color.Cyan,
        Color.DarkGray,
        Color.Gray,
        Color.Green,
        Color.LightGray,
        Color.Magenta,
        Color.Red
    ).map {
        it.copy(alpha = 0.6f)
    }
    val first = address.hashCode().absoluteValue % colourList.count()
    val second = address.length % colourList.count()
    return listOf(colourList[first], colourList[second])
}

@Composable
fun PaymentHistory(
    transactions: List<Transaction>,
    currentWallet: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        transactions.groupBy {
            fun instantToDate(timestamp: Instant): LocalDate {
                return timestamp.atZone(
                    ZoneId.systemDefault()
                ).toLocalDate()
            }

            when(it) {
                is Transaction.Celo -> {
                    instantToDate(it.timestamp)
                }
                is Transaction.Diem -> {
                    instantToDate(it.timestamp)
                }
            }
        }.forEach { (date, grouped) ->
            itemsIndexed(grouped) { index, tx ->
                val formatter = DateTimeFormatter.ofLocalizedDate(
                    FormatStyle.LONG
                )
                if (index == 0) {
                    PaymentHistoryDateRow(date.format(formatter))
                }
                when(tx) {
                    is Transaction.Celo -> {
                        val fTransfer = tx.tokenTransfer.firstOrNull()

                        fTransfer?.let {
                            val target = fTransfer.target(currentWallet)

                            PaymentHistoryRow(
                                image = {
                                    if (target != null) {
                                        val picUrl = target.profilePic
                                        if (picUrl != null) {
                                            Image(
                                                painter = rememberImagePainter(picUrl),
                                                contentDescription = null
                                            )
                                        } else {
                                            Box(
                                                modifier =
                                                Modifier
                                                    .background(
                                                        Brush.sweepGradient(
                                                            getRandomColors(target.address)
                                                        )
                                                    )
                                                    .fillMaxSize()
                                            )
                                        }
                                    } else {
                                        Image(painter = painterResource(
                                            R.drawable.ic_question_mark
                                        ), contentDescription = null)
                                    }
                                },
                                title = target?.name ?: stringResource(
                                    R.string.scr_payhistory__text__unknown_transaction_target
                                ),
                                amount = formatAmount(
                                    value = fTransfer.value,
                                    decimalPlaces = fTransfer.tokenDecimal
                                ),
                                showDivider = true
                            )
                        }
                    }
                    is Transaction.Diem -> {
                        val target = tx.target(currentWallet)

                        PaymentHistoryRow(
                            image = {
                                if (target != null) {
                                    val picUrl = target.profilePic
                                    if (picUrl != null) {
                                        Image(
                                            painter = rememberImagePainter(picUrl),
                                            contentDescription = null
                                        )
                                    } else {
                                        Box(
                                            modifier =
                                            Modifier
                                                .background(
                                                    Brush.sweepGradient(
                                                        getRandomColors(target.address)
                                                    )
                                                )
                                                .fillMaxSize()
                                        )
                                    }
                                } else {
                                    Image(painter = painterResource(
                                        R.drawable.ic_question_mark
                                    ), contentDescription = null)
                                }
                            },
                            title = target?.name ?: stringResource(
                                R.string.scr_payhistory__text__unknown_transaction_target
                            ),
                            amount = formatAmount(
                                value = tx.amount,
                                decimalPlaces = CurrencyConstant.DIEM_DECIMAL
                            ),
                            showDivider = true
                        )
                    }
                }
            }
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
                Box(
                    modifier =
                    Modifier
                        .background(
                            Brush.sweepGradient(
                                getRandomColors("ter4efw4s")
                            )
                        )
                        .fillMaxSize()
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

@Composable
fun PaymentHistoryDateRow(
    date: String,
    modifier: Modifier = Modifier
) {
    Text(
        date,
        modifier = modifier,
        maxLines = 1,
        style = MaterialTheme.typography.subtitle1
    )
}

@Preview
@Composable
fun PreviewPaymentHistoryDateRow() {
    PaymentHistoryDateRow(
        LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    )
}