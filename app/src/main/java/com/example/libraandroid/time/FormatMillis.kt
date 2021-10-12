package com.example.libraandroid.time

import java.util.concurrent.TimeUnit

// Format time as mm:ss or h:mm:ss format
fun formatMillis(timeInMillis: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis).toInt() % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis).toInt() % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis).toInt() % 60
    return when {
        hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
        minutes > 0 -> String.format("%02d:%02d", minutes, seconds)
        seconds > 0 -> String.format("00:%02d", seconds)
        else -> {
            "00:00"
        }
    }
}