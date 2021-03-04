package com.thomaskioko.stargazer.core.injection.module

import com.thomaskioko.stargazer.core.executor.CoroutineExecutionThread
import com.thomaskioko.stargazer.core.executor.CoroutineExecutionThreadImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface CoroutineExecutorModule {

    @get:[Binds Singleton]
    val CoroutineExecutionThreadImpl.coroutineExecutionThread: CoroutineExecutionThread
}
