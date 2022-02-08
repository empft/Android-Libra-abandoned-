package com.example.libraandroid.domain.finance.wallet.celo

import org.celo.contractkit.ContractKit
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jService
import org.web3j.protocol.http.HttpService

class CeloInteractor {
    fun test() {
        val kit = ContractKit.build(HttpService(""))
        kit.getTotalBalance("")
    }
}