package com.thomaskioko.stargazer.details.injection

import com.thomaskioko.stargazer.details.navigation.RepoDetailNavigationFactory
import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepoDetailNavigationModule {
    @Singleton
    @Binds
    @IntoSet
    fun bindRepoDetailNavigationFactory(factory: RepoDetailNavigationFactory): ComposeNavigationFactory
}
