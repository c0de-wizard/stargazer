package com.thomaskioko.githubstargazer.browse.injection.component

import com.thomaskioko.githubstargazer.browse.injection.module.ReposViewModelModule
import com.thomaskioko.githubstargazer.browse.ui.RepoDetailFragment
import com.thomaskioko.githubstargazer.browse.ui.RepoListFragment
import com.thomaskioko.githubstargazer.core.injection.component.ApplicationDependencies
import com.thomaskioko.githubstargazer.core.injection.component.applicationDependencies
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import com.thomaskioko.githubstargazer.core.viewmodel.getComponent
import com.thomaskioko.stargazer.navigation.NavigationDependencies
import com.thomaskioko.stargazer.navigation.navigationDeps
import dagger.Component

@ScreenScope
@Component(
    dependencies = [ApplicationDependencies::class, NavigationDependencies::class],
    modules = [
        ReposViewModelModule::class
    ]
)
interface RepoFeatureComponent {

    fun inject(fragment: RepoListFragment)

    fun inject(fragment: RepoDetailFragment)

    @Component.Factory
    interface Factory {

        fun create(
            applicationDependencies: ApplicationDependencies,
            navigationDeps: NavigationDependencies
        ): RepoFeatureComponent
    }
}

fun RepoListFragment.inject() {
    getComponent {
        DaggerRepoFeatureComponent.factory()
            .create(requireContext().applicationDependencies(), requireActivity().navigationDeps())
    }.inject(this)
}

fun RepoDetailFragment.inject() {
    getComponent {
        DaggerRepoFeatureComponent.factory()
            .create(requireContext().applicationDependencies(), requireActivity().navigationDeps())
    }.inject(this)
}
