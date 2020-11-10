package com.thomaskioko.githubstargazer.browse.injection

import com.thomaskioko.githubstargazer.browse.ui.RepoListFragment
import com.thomaskioko.githubstargazer.core.injection.component.ApplicationDependencies
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import com.thomaskioko.githubstargazer.repository.injection.DatabaseModule
import dagger.Component

@ScreenScope
@Component(
    dependencies = [ApplicationDependencies::class],
    modules = [DatabaseModule::class]
)
interface RepoFeatureComponent {

    fun inject(fragment: RepoListFragment)

    @Component.Factory
    interface Factory {

        fun create(applicationDependencies: ApplicationDependencies): RepoFeatureComponent
    }
}

