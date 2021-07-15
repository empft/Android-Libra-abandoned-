package com.example.libraandroid.extension

/**
 * Convert long in milliseconds to hh:mm:ss or mm:ss format.
 */
fun Long.formatTime(): String {
    val hour = this / 1000 / 60 / 60
    val min = this / 1000 / 60 % 60
    val sec = this / 1000 % 60

    return "${if (hour == 0L) "" else "$hour:"}$min:$sec"
}