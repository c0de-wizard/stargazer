package com.thomaskioko.stargazer.trending.injection

import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import com.thomaskioko.stargazer.trending.navigation.TrendingNavigationFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface TrendingNavigationModule {
    @Singleton
    @Binds
    @IntoSet
    fun bindBookmarkNavigation(factory: TrendingNavigationFactory): ComposeNavigationFactory
}
