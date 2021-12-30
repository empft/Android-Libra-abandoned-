package com.example.libraandroid.ui.transactionhistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.libraandroid.R
import com.example.libraandroid.ui.account.AppAccount
import com.example.libraandroid.ui.currency.formatAmount
import com.example.libraandroid.ui.wallet.Chain
import com.example.libraandroid.ui.wallet.Wallet
import com.example.libraandroid.ui.wallet.WalletContext
import java.math.BigInteger
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.absoluteValue

@Composable
fun TransactionHistoryRow(
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

    val amountMaxWidth = 0.4f

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
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                    modifier = Modifier
                        .fillMaxWidth(amountMaxWidth),
                    textAlign = TextAlign.End,
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
fun PreviewTransactionHistoryRow() {
    TransactionHistoryRow({
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

private data class TransferAmountData(
    val amount: BigInteger,
    val decimalPlaces: Int,
    val currency: String
)

@Composable
private fun SimpleTransactionRow(target: Wallet?, transfer: TransferAmountData) {
    val numberOfDigitsShown = 4

    TransactionHistoryRow(
        image = {
            if (target!= null) {
                val picUrl = target.walletContext?.appAccount?.profilePic
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
        title = target?.walletContext?.appAccount?.name ?: target?.address ?: stringResource(
            R.string.scr_payhistory__text__unknown_transaction_target
        ),
        amount = formatAmount(
            value = transfer.amount,
            decimalPlaces = transfer.decimalPlaces,
            currencyCode = transfer.currency,
            numberOfDigits = numberOfDigitsShown
        ),
        showDivider = true
    )
}

@Composable
fun TransactionHistory(
    transactions: List<Transaction>,
    modifier: Modifier = Modifier
) {
    if (transactions.isNotEmpty()) {
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
                        TransactionHistoryDateRow(date.format(formatter))
                    }

                    when(tx) {
                        is Transaction.Celo -> {
                            val fTransfer = tx.tokenTransfer.firstOrNull()

                            if (fTransfer != null) {
                                val transferAmount = TransferAmountData(
                                    amount = fTransfer.amount(),
                                    decimalPlaces = fTransfer.decimalPlaces(),
                                    currency = fTransfer.currency()
                                )
                                val target = when(fTransfer.viewer) {
                                    TransactionViewer.Anonymous -> {
                                        null
                                    }
                                    TransactionViewer.Recipient -> {
                                        fTransfer.from
                                    }
                                    TransactionViewer.Sender, TransactionViewer.Self -> {
                                        fTransfer.to
                                    }
                                }

                                SimpleTransactionRow(target = target, transfer = transferAmount)
                            }
                        }
                        is Transaction.Diem -> {
                            val transferAmount = TransferAmountData(
                                amount = tx.amount(),
                                decimalPlaces = tx.decimalPlaces(),
                                currency = tx.currency()
                            )
                            val target = when(tx.viewer) {
                                TransactionViewer.Anonymous -> {
                                    null
                                }
                                TransactionViewer.Recipient -> {
                                    tx.sender
                                }
                                TransactionViewer.Sender, TransactionViewer.Self -> {
                                    tx.receiver
                                }
                            }
                            SimpleTransactionRow(target = target, transfer = transferAmount)
                        }
                    }
                }
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            Text(
                stringResource(id = R.string.scr_payhistory__text__no_history),
                style = MaterialTheme.typography.h5,
                modifier = modifier
                    .rotate(-45f)
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewTransactionHistory() {
    val diem = Transaction.Diem(
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
            address = "myaddress",
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
        amount = 232000000UL,
        currency = "XUS",
        metadata = "metadata",
        viewer = TransactionViewer.Recipient
    )
    val celo = Transaction.Celo(
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
                viewer = TransactionViewer.Anonymous
            ),
            Transaction.Celo.Transfer(
                transactionIndex = 0,
                logIndex = 0,
                from = Wallet.Celo(
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
                value = BigInteger("134255225253253123155155351515515351531535"),
                contractAddress = "contractAddress",
                tokenDecimal = 18,
                tokenName = "CELO",
                tokenSymbol = "C$",
                viewer = TransactionViewer.Recipient
            )
        )
    )
    val rList = listOf(
        diem,
        diem.copy(
            receiver = Wallet.Diem(
                address = "senderAddress",
                chain = Chain.Diem(
                    id = 0
                )
            ),
            amount = 12342UL
        ),
        celo
    )

    TransactionHistory(transactions = rList)
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewEmptyTransactionHistory() {
    val emptyList = listOf<Transaction>()

    TransactionHistory(
        transactions = emptyList
    )
}

@Composable
fun TransactionHistoryDateRow(
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
fun PreviewTransactionHistoryDateRow() {
    TransactionHistoryDateRow(
        LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    )
}