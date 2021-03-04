package com.thomaskioko.stargazer.core.interactor

import com.thomaskioko.stargazer.core.ViewStateResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class Interactor<Params, Type>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    protected abstract fun run(params: Params): Flow<ViewStateResult<Type>>

    operator fun invoke(params: Params): Flow<ViewStateResult<Type>> =
        run(params).flowOn(coroutineDispatcher)
}

operator fun <Type> Interactor<Unit, Type>.invoke(): Flow<ViewStateResult<Type>> = this(Unit)
