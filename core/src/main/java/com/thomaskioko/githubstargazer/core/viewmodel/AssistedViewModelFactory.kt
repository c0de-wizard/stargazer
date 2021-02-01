package com.thomaskioko.githubstargazer.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface AssistedViewModelFactory<T> {
    fun create(data: T): ViewModel
}

fun <T> create(
    assistedFactory: AssistedViewModelFactory<T>,
    data: T
) = object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val clazz: ViewModel = assistedFactory.create(data)
        if (!modelClass.isAssignableFrom(clazz::class.java))
            throw IllegalArgumentException("Unknown model class $modelClass")
        try {
            return clazz as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}