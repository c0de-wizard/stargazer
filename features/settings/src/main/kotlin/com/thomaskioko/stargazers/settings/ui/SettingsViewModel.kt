package com.thomaskioko.stargazers.settings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.IoDispatcher
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazers.settings.domain.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<SettingsActions, SettingsViewState, CoroutineDispatcher>(
    initialViewState = SettingsViewState.Loading,
    dispatcher = ioDispatcher
) {

    //TODO:: Remove and use actionState instead
    private val mutableIsNightMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNightMode: LiveData<Boolean> get() = mutableIsNightMode

    fun isNightMode(): Boolean {
        return requireNotNull(isNightMode.value)
    }

    fun setNightMode(isEnabled: Boolean) {
        if (mutableIsNightMode.value != isEnabled) {
            mutableIsNightMode.value = isEnabled
        }
    }

    override fun handleAction(action: SettingsActions) {
        when (action) {
            SettingsActions.LoadTheme -> {
                settingsManager.getUiModeFlow()
                    .onEach { mutableViewState.emit(it.reduce()) }
                    .launchIn(ioScope)
            }
            is SettingsActions.UpdateTheme -> {
                viewModelScope.launch(context = ioDispatcher) {
                    settingsManager.setUiMode(action.nightModeSetting)
                }
            }
        }
    }
}

internal fun ViewStateResult<Int>.reduce(): SettingsViewState {
    return when (this) {
        is ViewStateResult.Error -> SettingsViewState.Error(message)
        is ViewStateResult.Loading -> SettingsViewState.Loading
        is ViewStateResult.Success -> SettingsViewState.ThemeLoaded(data)
    }
}
