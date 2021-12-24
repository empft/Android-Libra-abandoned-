package com.example.libraandroid.ui.transactionhistory

sealed interface TransactionViewer {
    object Sender: TransactionViewer
    object Recipient: TransactionViewer
    object Self: TransactionViewer
    object Anonymous: TransactionViewer
}
