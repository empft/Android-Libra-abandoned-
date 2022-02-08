package com.example.libraandroid.domain.finance.wallet.diem

import com.diem.DiemClient

class DiemInteractor(
    private val client: DiemClient
) {
    fun test() {
        client.getAccount("").balancesList



    }
}