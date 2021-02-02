package com.thomaskioko.githubstargazer.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.core.presentation.ViewAction
import com.thomaskioko.githubstargazer.core.presentation.ViewIntent
import com.thomaskioko.githubstargazer.core.presentation.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel<INTENT : ViewIntent, ACTION : ViewAction, STATE : ViewState>(
    initialViewState: STATE,
) : ViewModel() {

    protected val channelState = Channel<STATE>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).apply { offer(initialViewState) }

    val state: StateFlow<STATE> = channelState.receiveAsFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    fun dispatchIntent(intent: INTENT) {
        handleAction(intentToAction(intent))
    }

    abstract fun intentToAction(intent: INTENT): ACTION
    abstract fun handleAction(action: ACTION)
}
