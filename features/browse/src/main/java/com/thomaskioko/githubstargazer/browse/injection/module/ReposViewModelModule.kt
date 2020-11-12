package com.thomaskioko.githubstargazer.browse.injection.module

import androidx.lifecycle.ViewModel
import com.thomaskioko.githubstargazer.browse.ui.viewmodel.GetReposViewModel
import com.thomaskioko.githubstargazer.core.injection.scope.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ReposViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GetReposViewModel::class)
    fun bindViewModel(homeViewModel: GetReposViewModel): ViewModel
}
