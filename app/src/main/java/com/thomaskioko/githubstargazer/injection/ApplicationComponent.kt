package com.thomaskioko.githubstargazer.injection

import android.content.Context
import com.thomaskioko.githubstargazer.core.injection.component.ApplicationDependencies
import com.thomaskioko.githubstargazer.repository.injection.DatabaseModule
import com.thomaskioko.githubstargazer.repository.injection.GitHubApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        GitHubApiModule::class,
        DatabaseModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface ApplicationComponent : ApplicationDependencies {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
