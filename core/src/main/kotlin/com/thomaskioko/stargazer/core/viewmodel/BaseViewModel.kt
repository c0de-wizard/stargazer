package com.thomaskioko.stargazer.core.viewmodel

import androidx.lifecycle.ViewModel
import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel<ACTION : ViewAction, STATE : ViewState, DISPATCHER : CoroutineDispatcher>
    (initialViewState: STATE, dispatcher: DISPATCHER) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    val ioScope = CoroutineScope(dispatcher + viewModelJob)

    protected val channelState = Channel<STATE>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply { offer(initialViewState) }

    val state: StateFlow<STATE> = channelState.receiveAsFlow()
        .stateIn(ioScope, SharingStarted.Lazily, initialViewState)


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
