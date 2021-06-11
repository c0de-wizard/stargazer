package com.thomaskioko.stargazer.browse.injection

import com.thomaskioko.stargazer.browse.navigation.SearchReposNavigationFactory
import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface SearchReposNavigationModule {
    @Singleton
    @Binds
    @IntoSet
    fun bindSearchReposNavigationFactory(factory: SearchReposNavigationFactory): ComposeNavigationFactory
}
