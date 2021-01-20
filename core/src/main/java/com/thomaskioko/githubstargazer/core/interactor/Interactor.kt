package com.thomaskioko.githubstargazer.core.interactor

import com.thomaskioko.githubstargazer.core.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class Interactor<Params, Type>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    protected abstract fun run(params: Params): Flow<ViewState<Type>>

    operator fun invoke(params: Params): Flow<ViewState<Type>> =
        run(params).flowOn(coroutineDispatcher)
}

operator fun <Type> Interactor<Unit, Type>.invoke(): Flow<ViewState<Type>> = this(Unit)
