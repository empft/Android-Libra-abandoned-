package com.example.libraandroid.ui.transactionhistory

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import android.icu.util.ULocale
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val rowHorizontalPadding = 8.dp
private val rowVerticalPadding = 4.dp

@Composable
private fun TransactionDetailRow(
    leading: @Composable () -> Unit,
    trailing: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    val typography = MaterialTheme.typography
    val styledLeading = @Composable {
        ProvideTextStyle(typography.subtitle1) {
            leading()
        }
    }

    val styledTrailing = @Composable {
        ProvideTextStyle(typography.subtitle1) {
            trailing()
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = rowHorizontalPadding,
                vertical = rowVerticalPadding
            )
    ) {
        Box(
            Modifier
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.CenterStart
        ) { styledLeading() }

        Spacer(Modifier.widthIn(min = 16.dp))

        Box(
            Modifier
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.CenterStart
        ) { styledTrailing() }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewTransactionDetailRow() {
    TransactionDetailRow(
        leading = {
            Text("Item")
        },
        trailing = {
            Text("Value")
        }
    )
}

@Composable
fun TransactionDetailGroup(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val typography = MaterialTheme.typography

    val styledTitle = @Composable {
        ProvideTextStyle(typography.caption) {
            title?.let {
                it()
            }
        }
    }

    val startPadding = 16.dp
    val endPadding = 16.dp
    
    Column(modifier = modifier
        .padding(
            start = startPadding,
            end = endPadding
        )
    ) {
        styledTitle()
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            modifier = Modifier
                .wrapContentSize(),
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            shape = RoundedCornerShape(4.dp),
            elevation = 4.dp
        ) {
            content()
        }
    }

}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewTransactionDetailGroup() {
    TransactionDetailGroup(
        title = {
            Text("Summary")
        },
        content = {
            Column {
                TransactionDetailRow(
                    leading = {
                        Text("Item")
                    },
                    trailing = {
                        Text("Value")
                    }
                )
                Divider()

                TransactionDetailRow(
                    leading = {
                        Text("")
                    },
                    trailing = {
                        Text("Vtggggggggggggggggggggggggggggggggggggggggggggggggalue")
                    }
                )
            }
        }
    )
}

private fun printJsonCelo(tx: Transaction.Celo): String {
    //TODO: Convert to pretty print, ie: change it to encodeToString after experimental ends
    val format = Json { prettyPrint = true }
    return format.encodeToJsonElement(RawCelo.from(tx)).toString()
}

@Composable
private fun formatAmount(
    value: BigInteger,
    tokenDecimal: Int,
): String {
    val number = value
        .toBigDecimal(mathContext = MathContext(value.toString().length))
        .divide(BigDecimal.TEN.pow(tokenDecimal),tokenDecimal,RoundingMode.UNNECESSARY)
        .stripTrailingZeros()

    val scale = number.scale()

    val pattern: String = if (scale <= 0) {
        "#,###"
    } else {
        "#,###." + "#".repeat(scale)
    }

    val locale = LocalContext.current.resources.configuration.locales[0]
    return DecimalFormat(pattern, DecimalFormatSymbols(locale)).format(number)
}

@Composable
private fun CeloTransactionDetail(
    transaction: Transaction.Celo,
    currentWallet: String,
    modifier: Modifier = Modifier,
    showRawJson: Boolean = false
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        TransactionDetailGroup(title = {
           Text(stringResource(
               R.string.scr_payhistory__text__transfer
           ))
        }) {
            Column {
                transaction.tokenTransfer.forEach {
                    val isFrom = it.from.address == currentWallet
                    val isTo = it.to.address == currentWallet


                    if (isFrom || isTo) {
                        if (isFrom && !isTo) {
                            TransactionDetailRow(
                                leading = {
                                    Text(it.to.name ?: it.to.address)
                                },
                                trailing = {
                                    Text(
                                        it.tokenSymbol +
                                                formatAmount(it.value, it.tokenDecimal)
                                    )
                                }
                            )

                        } else if (!isFrom && isTo) {
                            TransactionDetailRow(
                                leading = {
                                    Text(it.from.name ?: it.from.address)
                                },
                                trailing = {
                                    Text(
                                        "+" + it.tokenSymbol +
                                                formatAmount(it.value, it.tokenDecimal)
                                    )
                                }
                            )

                        } else {

                            TransactionDetailRow(
                                leading = {
                                    Text(stringResource(
                                        R.string.scr_payhistory__text__self
                                    ))
                                },
                                trailing = {
                                    Text(
                                        "=" + it.tokenSymbol +
                                                formatAmount(it.value, it.tokenDecimal),
                                        color = Color.LightGray
                                    )
                                }
                            )
                        }
                        Divider()
                    }
                }
            }
        }

        TransactionDetailGroup {
            TransactionDetailRow(
                leading = {
                    Text(stringResource(
                        R.string.scr_payhistory__text__date
                    ))
                },
                trailing = {
                    val config = LocalContext.current.resources.configuration
                    val formatter = DateTimeFormatter
                        .ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(config.locales.get(0))
                        .withZone(ZoneId.systemDefault())
                    Text(formatter.format(transaction.timestamp))
                }
            )
        }

        TransactionDetailGroup(title = {
            Text(stringResource(
                R.string.scr_payhistory__text__fees
            ))
        }) {
            Column {
                TransactionDetailRow(
                    leading = {
                        Text(stringResource(
                            R.string.scr_payhistory__text__gas_fees
                        ))
                    }, trailing = {
                        Text(formatAmount(
                            transaction.gasPrice * BigInteger(transaction.gasUsed.toString()
                            ), transaction.gasDecimal
                        ))
                    }
                )

                if (transaction.gatewayFee > BigInteger.ZERO) {
                    Divider()
                    TransactionDetailRow(
                        leading = {
                            Text(stringResource(
                                R.string.scr_payhistory__text__gateway_fee
                            ))
                        }, trailing = {
                            Text(formatAmount(
                                transaction.gatewayFee, transaction.gatewayCurrencyDecimal
                            ))
                        }
                    )
                }
            }
        }

        if (showRawJson) {
            TransactionDetailGroup(title = {
                Text(stringResource(R.string.scr_payhistory__text__json))
            }) {
                Text(printJsonCelo(transaction), modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun DiemTransactionDetail() {

}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewCeloTransactionDetail() {
    CeloTransactionDetail(
        transaction = Transaction.Celo(
            blockHash = "blockhash",
            blockNumber = 10000UL,
            cumulativeGasUsed = BigInteger.ONE,
            hash = "hash",
            input = "input",
            nonce = 2UL,
            timestamp = Instant.EPOCH,
            gatewayFee = BigInteger.TEN,
            feeRecipient = "feeRecipient",
            gatewayCurrency = "currencyAddress",
            gas = 1000UL,
            gasPrice = BigInteger.TEN,
            gasUsed = 500UL,
            gasDecimal = 18,
            gatewayCurrencySymbol = "CELO",
            gatewayCurrencyDecimal = 18,
            tokenTransfer = listOf(
                Transaction.Celo.Transfer(
                    transactionIndex = 0,
                    logIndex = 0,
                    from = AddressWithId(0, "from", "from"),
                    to = AddressWithId(1, "to", "to"),
                    value = BigInteger.TEN,
                    contractAddress = "contractAddress",
                    tokenDecimal = 18,
                    tokenName = "CELO",
                    tokenSymbol = "C$"
                ),
                Transaction.Celo.Transfer(
                    transactionIndex = 0,
                    logIndex = 0,
                    from = AddressWithId(1, "to", "to"),
                    to = AddressWithId(0, "from", "from"),
                    value = BigInteger("134255225253253123155155351515515351531535"),
                    contractAddress = "contractAddress",
                    tokenDecimal = 18,
                    tokenName = "CELO",
                    tokenSymbol = "C$"
                )
            )
        ),
        currentWallet = "from",
        showRawJson = true
    )
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewDiemTransactionDetail() {
    DiemTransactionDetail()
}

@Composable
fun TransactionDetail(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    when(transaction) {
        is Transaction.Celo -> {
            Text("Celo")
        }
        is Transaction.Diem -> {
            Text("Diem")
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewTransactionDetail() {
    TransactionDetail(transaction = Transaction.Celo(
        blockHash = "blockhash",
        blockNumber = 10000UL,
        cumulativeGasUsed = BigInteger.ONE,
        hash = "hash",
        input = "input",
        nonce = 2UL,
        timestamp = Instant.EPOCH,
        gatewayFee = BigInteger.TEN,
        feeRecipient = "feeRecipient",
        gatewayCurrency = "currencyAddress",
        gas = 1000UL,
        gasPrice = BigInteger.TEN,
        gasUsed = 500UL,
        gasDecimal = 18,
        gatewayCurrencySymbol = "CELO",
        gatewayCurrencyDecimal = 18,
        tokenTransfer = listOf(
            Transaction.Celo.Transfer(
                transactionIndex = 0,
                logIndex = 0,
                from = AddressWithId(0, "from", "from"),
                to = AddressWithId(1, "to", "to"),
                value = BigInteger.TEN,
                contractAddress = "contractAddress",
                tokenDecimal = 18,
                tokenName = "CELO",
                tokenSymbol = "C$"
        ))
    ))
}