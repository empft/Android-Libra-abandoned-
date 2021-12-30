package com.example.libraandroid.ui.transactionhistory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R
import com.example.libraandroid.ui.account.AppAccount
import com.example.libraandroid.ui.currency.*
import com.example.libraandroid.ui.wallet.Chain
import com.example.libraandroid.ui.wallet.Wallet
import com.example.libraandroid.ui.wallet.WalletContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.math.BigInteger
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
                .align(Alignment.CenterVertically)
                .fillMaxWidth(0.3f),
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

@Composable
fun formatTimestamp(timestamp: Instant): String {
    val config = LocalContext.current.resources.configuration
    val formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(config.locales.get(0))
        .withZone(ZoneId.systemDefault())
    return formatter.format(timestamp)
}

private fun printJsonCelo(tx: Transaction.Celo): String {
    //TODO: Convert to pretty print, ie: change it to encodeToString after experimental ends
    val format = Json { prettyPrint = true }
    return format.encodeToJsonElement(RawCelo.from(tx)).toString()
}

@Composable
private fun CeloTransactionDetail(
    transaction: Transaction.Celo,
    modifier: Modifier = Modifier,
    showRawJson: Boolean = false
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.verticalScroll(
            rememberScrollState()
        )
    ) {
        TransactionDetailGroup(title = {
           Text(stringResource(
               R.string.scr_payhistory__text__transfer
           ))
        }) {
            Column {
                transaction.tokenTransfer.forEachIndexed { index, transfer ->
                    val target = when(transfer.viewer) {
                        TransactionViewer.Anonymous -> {
                            null
                        }
                        TransactionViewer.Recipient -> {
                            transfer.from
                        }
                        TransactionViewer.Sender, TransactionViewer.Self -> {
                            transfer.to
                        }
                    }

                    if (target != null) {
                        TransactionDetailRow(
                            leading = {
                                Text(
                                    text = target.walletContext?.name ?: target.address,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                            },
                            trailing = {
                                TransferAmountUi(
                                    transfer = transfer
                                )
                            }
                        )
                    } else {
                        Column {
                            TransactionDetailRow(
                                leading = {
                                    Text(
                                        text = stringResource(R.string.scr_payhistory__text__from)
                                    )
                                },
                                trailing = {
                                    Text(
                                        text = transfer.from.walletContext?.name ?: transfer.from.address,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            )

                            TransactionDetailRow(
                                leading = {
                                    Text(
                                        text = stringResource(R.string.scr_payhistory__text__to)
                                    )
                                },
                                trailing = {
                                    Text(
                                        text = transfer.to.walletContext?.name ?: transfer.to.address,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            )

                            TransactionDetailRow(
                                leading = {
                                    Text(
                                        text = stringResource(R.string.scr_payhistory__text__amount)
                                    )
                                },
                                trailing = {
                                    TransferAmountUi(
                                        transfer = transfer
                                    )
                                }
                            )
                        }
                    }


                    if (index != transaction.tokenTransfer.lastIndex) {
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
                    Text(formatTimestamp(transaction.timestamp))
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
                            ), transaction.gasDecimal, transaction.gasCurrencySymbol
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
                                transaction.gatewayFee, transaction.gatewayCurrencyDecimal,
                                transaction.gatewayCurrencySymbol
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

private fun printJsonDiem(tx: Transaction.Diem): String {
    //TODO: Convert to pretty print, ie: change it to encodeToString after experimental ends
    val format = Json { prettyPrint = true }
    return format.encodeToJsonElement(RawDiem.from(tx)).toString()
}

@Composable
private fun DiemTransactionDetail(
    transaction: Transaction.Diem,
    modifier: Modifier = Modifier,
    showRawJson: Boolean = false
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.verticalScroll(
            rememberScrollState()
        )
    ) {
        TransactionDetailGroup(title = {
            Text(stringResource(R.string.scr_payhistory__text__summary))
        }) {
            Column {
                TransactionDetailRow(
                    leading = {
                        Text(
                            stringResource(R.string.scr_payhistory__text__date)
                        )
                    },
                    trailing = {
                        Text(formatTimestamp(transaction.timestamp))
                    }
                )
                Divider()
                TransactionDetailRow(
                    leading = {
                        Text(
                            stringResource(R.string.scr_payhistory__text__from)
                        )
                    },
                    trailing = {
                        val sender = transaction.sender
                        Text(
                            text = sender.walletContext?.name ?: sender.address,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
                Divider()
                TransactionDetailRow(
                    leading = {
                        Text(
                            stringResource(R.string.scr_payhistory__text__to)
                        )
                    },
                    trailing = {
                        val recipient = transaction.receiver
                        Text(
                            text = recipient.walletContext?.name ?: recipient.address,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
                Divider()
                TransactionDetailRow(
                    leading = {
                        Text(
                            stringResource(R.string.scr_payhistory__text__amount)
                        )
                    },
                    trailing = {
                        TransferAmountUi(
                            transfer = transaction
                        )
                    }
                )
            }
        }

        TransactionDetailGroup(title = {
            Text(
                stringResource(
                    R.string.scr_payhistory__text__fees
                )
            )
        }) {
            Column {
                TransactionDetailRow(
                    leading = {
                        Text(
                            stringResource(R.string.scr_payhistory__text__gas_fees)
                        )
                    },
                    trailing = {
                        Text(formatAmount(
                                value = transaction.gasUsed * transaction.gasUnitPrice,
                                decimalPlaces = CurrencyConstant.DIEM_DECIMAL,
                                transaction.gasCurrency
                            )
                        )
                    }
                )
            }
        }

        if (showRawJson) {
            TransactionDetailGroup(title = {
                Text(stringResource(R.string.scr_payhistory__text__json))
            }) {
                Text(printJsonDiem(transaction), modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
                )
            }
        }

    }
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
            gasCurrencySymbol = "CELO",
            gatewayCurrencySymbol = "CELO",
            gatewayCurrencyDecimal = 18,
            tokenTransfer = listOf(
                Transaction.Celo.Transfer(
                    transactionIndex = 0,
                    logIndex = 0,
                    from = Wallet.Celo(
                        address = "senderAddress",
                        chain = Chain.Celo(
                            id = 0
                        ),
                        walletContext = WalletContext(
                            appAccount = AppAccount(
                                id = 0,
                                name = "from"
                            )
                        )
                    ),
                    to = Wallet.Celo(
                        address = "receiverAddress",
                        chain = Chain.Celo(
                            id = 0
                        ),
                        walletContext = WalletContext(
                            appAccount = AppAccount(
                                id = 0,
                                name = "to"
                            )
                        )
                    ),
                    value = BigInteger.TEN,
                    contractAddress = "contractAddress",
                    tokenDecimal = 18,
                    tokenName = "CELO",
                    tokenSymbol = "C$",
                    viewer = TransactionViewer.Self
                ),
                Transaction.Celo.Transfer(
                    transactionIndex = 0,
                    logIndex = 0,
                    from = Wallet.Celo(
                        address = "senderAddress2",
                        chain = Chain.Celo(
                            id = 0
                        ),
                        walletContext = WalletContext(
                            appAccount = AppAccount(
                                id = 0,
                                name = "from2"
                            )
                        )
                    ),
                    to = Wallet.Celo(
                        address = "receiverAddress2",
                        chain = Chain.Celo(
                            id = 0
                        ),
                        walletContext = WalletContext(
                            appAccount = AppAccount(
                                id = 0,
                                name = "to2"
                            )
                        )
                    ),
                    value = BigInteger("134255225253253123155155351515515351531535"),
                    contractAddress = "contractAddress",
                    tokenDecimal = 18,
                    tokenName = "CELO",
                    tokenSymbol = "C$",
                    viewer = TransactionViewer.Sender
                )
            )
        ),
        showRawJson = true
    )
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewDiemTransactionDetail() {
    DiemTransactionDetail(
        transaction = Transaction.Diem(
            version = 143242UL,
            vmStatus = Transaction.Diem.VmStatus(
                type = "P2P"
            ),
            timestamp = Instant.now(),
            sender = Wallet.Diem(
                address = "senderAddress",
                chain = Chain.Diem(
                    id = 0
                )
            ),
            receiver = Wallet.Diem(
                address = "myAddress",
                chain = Chain.Diem(
                    id = 0
                )
            ),
            publicKey = "e4f5a62d",
            sequenceNumber = 1000UL,
            chainId = 2U,
            maxGasAmount = 100UL,
            gasUnitPrice = 1000UL,
            gasUsed = 50UL,
            gasCurrency = "XDX",
            expirationTimestamp = Instant.now().plusMillis(100000L),
            amount = 2000000UL,
            currency = "XUS",
            metadata = "metadata",
            viewer = TransactionViewer.Recipient
        ),
        showRawJson = true
    )
}

@Composable
fun TransactionDetail(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    showRawJson: Boolean = false
) {
    when(transaction) {
        is Transaction.Celo -> {
            CeloTransactionDetail(
                transaction = transaction,
                modifier = modifier,
                showRawJson = showRawJson
            )
        }
        is Transaction.Diem -> {
            DiemTransactionDetail(
                transaction = transaction,
                modifier = modifier,
                showRawJson = showRawJson
            )
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
        gasCurrencySymbol = "CELO",
        gatewayCurrencySymbol = "CELO",
        gatewayCurrencyDecimal = 18,
        tokenTransfer = listOf(
            Transaction.Celo.Transfer(
                transactionIndex = 0,
                logIndex = 0,
                from = Wallet.Celo(
                    address = "from",
                    chain = Chain.Celo(
                        id = 0
                    ),
                    walletContext = WalletContext(
                        appAccount = AppAccount(
                            id = 0,
                            name = "from"
                        )
                    )
                ),
                to = Wallet.Celo(
                        address = "to",
                chain = Chain.Celo(
                    id = 0
                ),
                    walletContext = WalletContext(
                        appAccount = AppAccount(
                            id = 0,
                            name = "to"
                        )
                    )
            ),
                value = BigInteger.TEN,
                contractAddress = "contractAddress",
                tokenDecimal = 18,
                tokenName = "CELO",
                tokenSymbol = "C$",
                viewer = TransactionViewer.Anonymous
        ))
    ))
}