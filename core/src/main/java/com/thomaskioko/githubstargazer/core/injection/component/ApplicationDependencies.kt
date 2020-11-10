package com.thomaskioko.githubstargazer.core.injection.component

import android.content.Context
import com.thomaskioko.githubstargazer.repository.api.GithubRepository

interface ApplicationDependencies {

    fun appContext(): Context

    fun githubRepository(): GithubRepository
}

fun Context.applicationDependencies(): ApplicationDependencies {
    return (applicationContext as? HasApplicationDependencies)?.getApplicationDependencies()
        ?: throw IllegalArgumentException("Application must implement HasApplicationDependencies")
}