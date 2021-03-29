package com.thomaskioko.stargazer.core.viewmodel

import androidx.lifecycle.ViewModel
import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel<ACTION : ViewAction, STATE : ViewState>(
    initialViewState: STATE,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    val ioScope = CoroutineScope(dispatcher + viewModelJob)

    protected val mutableViewState: MutableStateFlow<STATE> =
        MutableStateFlow(initialViewState)

    val actionState: SharedFlow<STATE> get() = mutableViewState

    fun dispatchAction(action: ACTION) {
        handleAction(action)
    }

    abstract fun handleAction(action: ACTION)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
