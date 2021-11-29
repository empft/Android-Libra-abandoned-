package com.example.libraandroid.ui.transactionhistory

sealed interface TransactionDirection {
    object Sender: TransactionDirection
    object Recipient: TransactionDirection
    object Self: TransactionDirection
    object Neither: TransactionDirection
}