package com.thomaskioko.githubstargazer.core.injection.component

import android.content.Context

interface ApplicationDependencies {

    fun appContext(): Context
}

fun Context.applicationDependencies(): ApplicationDependencies {
    return (applicationContext as? HasApplicationDependencies)?.getApplicationDependencies()
        ?: throw IllegalArgumentException("Application must implement HasApplicationDependencies")
}