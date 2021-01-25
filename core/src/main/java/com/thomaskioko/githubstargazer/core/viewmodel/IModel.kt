package com.thomaskioko.githubstargazer.core.viewmodel

import androidx.lifecycle.LiveData

interface IModel<STATE, INTENT> {

    val state: LiveData<STATE>

    fun dispatchIntent(intent: INTENT)
}
