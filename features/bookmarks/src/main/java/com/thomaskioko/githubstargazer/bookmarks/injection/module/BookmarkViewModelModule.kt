package com.thomaskioko.githubstargazer.bookmarks.injection.module

import androidx.lifecycle.ViewModel
import com.thomaskioko.githubstargazer.bookmarks.ui.viewmodel.GetBookmarkedReposViewModel
import com.thomaskioko.githubstargazer.core.injection.scope.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BookmarkViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GetBookmarkedReposViewModel::class)
    fun bindViewModel(viewModel: GetBookmarkedReposViewModel): ViewModel

}