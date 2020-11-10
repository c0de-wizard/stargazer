package com.thomaskioko.githubstargazer

import android.app.Application
import com.thomaskioko.githubstargazer.core.injection.component.HasApplicationDependencies
import com.thomaskioko.githubstargazer.injection.DaggerApplicationComponent

class GithubApplication : Application(), HasApplicationDependencies {

    private val appComponent by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun getApplicationDependencies() = appComponent
}