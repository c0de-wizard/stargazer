package com.thomaskioko.githubstargazer

import android.app.Application
import com.thomaskioko.githubstargazer.core.injection.component.HasApplicationDependencies
import com.thomaskioko.githubstargazer.injection.DaggerApplicationComponent
import timber.log.Timber

class GithubApplication : Application(), HasApplicationDependencies {

    private val appComponent by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getApplicationDependencies() = appComponent
}
