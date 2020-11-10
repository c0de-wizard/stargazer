package com.thomaskioko.githubstargazer.injection

import android.content.Context
import com.thomaskioko.githubstargazer.core.injection.component.ApplicationDependencies
import com.thomaskioko.githubstargazer.repository.GithubRepository
import com.thomaskioko.githubstargazer.repository.injection.GitHubApiModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GitHubApiModule::class])
interface ApplicationComponent : ApplicationDependencies {

    fun githubRepository(): GithubRepository

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
