package com.thomaskioko.githubstargazer.repository

import com.thomaskioko.stargazer.core.executor.CoroutineExecutionThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

internal class TestCoroutineExecutionThread : CoroutineExecutionThread {

    override val main: CoroutineDispatcher
        get() = TestCoroutineDispatcher()

    override val io: CoroutineDispatcher
        get() = TestCoroutineDispatcher()

    override val default: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
}
