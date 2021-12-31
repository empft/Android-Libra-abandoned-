package com.example.libraandroid.ui.balance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.libraandroid.R
import com.example.libraandroid.ui.currency.formatAmount
import com.example.libraandroid.ui.wallet.Chain
import com.example.libraandroid.ui.wallet.Wallet
import java.math.BigInteger
import kotlin.jvm.internal.Intrinsics

@Composable
fun BalanceByWallet(
    balances: List<Balance>
) {
    if (balances.isNotEmpty()) {
        LazyColumn {
            balances.groupBy {
                when(it) {
                    is Balance.Celo -> {
                        it.wallet
                    }
                    is Balance.Diem -> {
                        it.wallet
                    }
                }
            }.forEach{ (wallet, grouped) ->
                itemsIndexed(grouped) { index, balance ->
                    if (index == 0) {
                        BalanceByWalletNameRow(
                            walletName = wallet.walletContext?.name ?: stringResource(
                                R.string.scr_balance__text__unnamed_wallet
                            ),
                            chainName = wallet.chain.chainContext?.name ?: stringResource(
                                R.string.scr_balance__text__chain_alt_name,
                                wallet.chain.id
                            ),
                            address = wallet.address
                        )
                    }

                    when(balance) {
                        is Balance.Celo -> {
                            BalanceByWalletRow(
                                currency = balance.currency, amount = formatAmount(
                                    value = balance.amount,
                                    decimalPlaces = balance.decimalPlaces
                                )
                            )
                        }
                        is Balance.Diem -> {
                            BalanceByWalletRow(
                                currency = balance.currency, amount = formatAmount(
                                    value = balance.amount,
                                    decimalPlaces = balance.decimalPlaces
                                )
                            )
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBalanceByWallet() {
    BalanceByWallet(balances = listOf(
        Balance.Diem(
            amount = 10000UL,
            currency = "DIEM USD",
            wallet = Wallet.Diem(
                chain = Chain.Diem(
                    id = 10
                ),
                address = "address"
            )
        ),
        Balance.Celo(
            amount = BigInteger.TEN,
            currency = "CELO",
            decimalPlaces = 18,
            wallet = Wallet.Celo(
                chain = Chain.Celo(
                    id = 20
                ),
                address = "celoAddress"
            )
        )
    ))
}