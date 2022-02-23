package com.example.libraandroid.domain.finance.wallet

import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.chain.Chain
import java.math.BigInteger

data class Balance(
    val amount: BigInteger,
    val currency: Currency,
    val wallet: Wallet
)