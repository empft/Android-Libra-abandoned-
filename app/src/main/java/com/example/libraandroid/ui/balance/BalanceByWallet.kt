package com.example.libraandroid.ui.balance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R
import com.example.libraandroid.ui.currency.Currency
import com.example.libraandroid.ui.currency.formatAmount
import com.example.libraandroid.ui.wallet.Chain
import com.example.libraandroid.ui.wallet.Wallet
import com.example.libraandroid.ui.wallet.WalletContext
import java.math.BigInteger

@Composable
fun BalanceByWallet(
    balances: List<Balance>,
    modifier: Modifier = Modifier
) {
    if (balances.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            balances.groupBy {
                it.wallet
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

                        Divider(startIndent = dimensionResource(R.dimen.scr_balance__list__horizontal_padding))
                    }

                    BalanceByWalletRow(
                        currency = balance.currency.code, amount = formatAmount(
                            value = balance.amount,
                            decimalPlaces = balance.currency.decimalPlaces
                        )
                    )

                    Divider()
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
            amount = BigInteger(10000UL.toString()),
            currency = Currency.Diem(
                code = "DIEM USD"
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
                tokenName = "Name",
                tokenAddress = "Address",
                decimalPlaces = 18,
                code = "CELO USD"
            ),
            wallet = Wallet.Celo(
                chain = Chain.Celo(
                    id = 20
                ),
                address = "celoAddress"
            )
        )
    ))
}