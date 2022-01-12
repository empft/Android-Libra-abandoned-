package com.example.libraandroid.ui.balance

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraandroid.R
import com.example.libraandroid.ui.currency.Currency
import com.example.libraandroid.ui.currency.formatAmount
import com.example.libraandroid.ui.wallet.Chain
import com.example.libraandroid.ui.wallet.Wallet
import com.example.libraandroid.ui.wallet.WalletContext
import java.math.BigInteger

@Composable
fun BalanceByCurrency(
    balances: List<Balance>,
    modifier: Modifier = Modifier
) {
    if (balances.isNotEmpty()) {
        LazyColumn {
            balances.groupBy {
                it.wallet.chain
            }.forEach { (chain, chainGrouped) ->
                item {
                    BalanceByCurrencyChainRow(
                        chainName = chain.chainContext?.name ?: stringResource(
                            R.string.scr_balance__text__chain_alt_name,
                            when(chain) {
                                is Chain.Celo -> stringResource(
                                    R.string.g__text__celo
                                )
                                is Chain.Diem -> stringResource(
                                    R.string.g__text__diem
                                )
                            },
                            chain.id
                        )
                    )
                    Divider(startIndent = dimensionResource(R.dimen.scr_balance__list__horizontal_padding))
                }

                chainGrouped.groupBy {
                    it.currency
                }.forEach { (currency, currencyGrouped) ->
                    itemsIndexed(currencyGrouped) { index, balance ->
                        if (index == 0) {
                            BalanceByCurrencyAmountTotalRow(
                                currency = currency.code, amount = formatAmount(
                                    value = currencyGrouped.sumOf {
                                        it.amount
                                    },
                                    decimalPlaces = currency.decimalPlaces
                                )
                            )
                        }

                        BalanceByCurrencyAmountWalletRow(
                            walletName = balance.wallet.walletContext?.name ?: balance.wallet.address,
                            amount = formatAmount(value = balance.amount, decimalPlaces = balance.currency.decimalPlaces)
                        )

                        if (index == currencyGrouped.lastIndex) {
                            Divider()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBalanceByCurrency() {
    BalanceByCurrency(balances = listOf(
        Balance.Diem(
            amount = BigInteger(10000UL.toString()),
            currency = Currency.Diem(
                code = "DUSD"
            ),
            wallet = Wallet.Diem(
                chain = Chain.Diem(
                    id = 10
                ),
                address = "address",
                walletContext = WalletContext(
                    name = "wallet 1"
                )
            )
        ),
        Balance.Celo(
            amount = BigInteger.TEN,
            currency = Currency.Celo(
                tokenAddress = "1d2f",
                tokenName = "Full Name",
                code = "CUSD",
                decimalPlaces = 18
            ),
            wallet = Wallet.Celo(
                chain = Chain.Celo(
                    id = 20
                ),
                address = "celoAddress"
            )
        ),
        Balance.Celo(
            amount = BigInteger.TEN,
            currency = Currency.Celo(
                tokenAddress = "1234fe",
                tokenName = "Name",
                code = "CEUR",
                decimalPlaces = 18
            ),
            wallet = Wallet.Celo(
                chain = Chain.Celo(
                    id = 20
                ),
                address = "anotherAddress"
            )
        ),
        Balance.Celo(
            amount = BigInteger.TEN,
            currency = Currency.Celo(
                tokenAddress = "2e",
                tokenName = "Name",
                code = "CGBP",
                decimalPlaces = 18
            ),
            wallet = Wallet.Celo(
                chain = Chain.Celo(
                    id = 20
                ),
                address = "anotherAddress",
                walletContext = WalletContext(
                    name = "anotherWallet"
                )
            )
        )
    ))
}