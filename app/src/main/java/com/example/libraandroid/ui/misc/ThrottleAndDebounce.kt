package com.example.libraandroid.ui.misc

import kotlinx.coroutines.*

class DelayedCall (
    private val timeMillis: Long = UiConstant.DefaultDelay,
    private val coroutineScope: CoroutineScope
) {
    var job: Job? = null

    fun throttleFirst(fn: suspend () -> Unit) {
        if (job?.isCompleted != false) {
            job = coroutineScope.launch {
                fn()
                delay(timeMillis)
            }
        }
    }

    fun throttleLatest(fn: suspend () -> Unit) {
        if (job?.isCompleted != false) {
            job = coroutineScope.launch {
                delay(timeMillis)
                fn()
            }
        }
    }

    fun debounce(fn: suspend () -> Unit) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(timeMillis)
            fn()
        }
    }
}