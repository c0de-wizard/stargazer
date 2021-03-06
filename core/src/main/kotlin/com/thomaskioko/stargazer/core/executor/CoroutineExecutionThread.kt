package com.thomaskioko.stargazer.core.executor

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineExecutionThread {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}
