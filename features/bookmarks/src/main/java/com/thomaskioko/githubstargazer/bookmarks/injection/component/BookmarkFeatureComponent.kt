package com.thomaskioko.githubstargazer.bookmarks.injection.component

import com.thomaskioko.githubstargazer.bookmarks.injection.module.BookmarkViewModelModule
import com.thomaskioko.githubstargazer.bookmarks.ui.BookmarkedReposFragment
import com.thomaskioko.githubstargazer.core.injection.component.ApplicationDependencies
import com.thomaskioko.githubstargazer.core.injection.component.applicationDependencies
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import com.thomaskioko.githubstargazer.core.viewmodel.getComponent
import dagger.Component

@ScreenScope
@Component(
    dependencies = [ApplicationDependencies::class],
    modules = [
        BookmarkViewModelModule::class
    ]
)
interface BookmarkFeatureComponent {

    fun inject(fragment: BookmarkedReposFragment)

    @Component.Factory
    interface Factory {

        fun create(applicationDependencies: ApplicationDependencies): BookmarkFeatureComponent
    }
}

fun BookmarkedReposFragment.inject() {
    getComponent {
        DaggerBookmarkFeatureComponent.factory()
            .create(requireContext().applicationDependencies())
    }.inject(this)
}

