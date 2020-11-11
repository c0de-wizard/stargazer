package com.thomaskioko.githubstargazer.browse.injection.component

import com.thomaskioko.githubstargazer.browse.injection.module.ReposViewModelModule
import com.thomaskioko.githubstargazer.browse.ui.RepoListFragment
import com.thomaskioko.githubstargazer.core.injection.component.ApplicationDependencies
import com.thomaskioko.githubstargazer.core.injection.component.applicationDependencies
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import com.thomaskioko.githubstargazer.core.viewmodel.getComponent
import dagger.Component

@ScreenScope
@Component(
    dependencies = [ApplicationDependencies::class],
    modules = [
        ReposViewModelModule::class
    ]
)
interface RepoFeatureComponent {

    fun inject(fragment: RepoListFragment)

    @Component.Factory
    interface Factory {

        fun create(applicationDependencies: ApplicationDependencies): RepoFeatureComponent
    }
}

fun RepoListFragment.inject() {
    getComponent {
        DaggerRepoFeatureComponent.factory()
            .create(requireContext().applicationDependencies())
    }.inject(this)
}
