package com.thomaskioko.githubstargazer.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel<INTENT : ViewIntent, STATE : ViewState> :
    ViewModel(), IModel<STATE, INTENT> {

    protected val mState = MutableLiveData<STATE>()
    override val state: LiveData<STATE>
        get() {
            return mState
        }

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    final override fun dispatchIntent(intent: INTENT) {
        handleIntent(intent)
    }

    abstract fun handleIntent(intent: INTENT)
}
