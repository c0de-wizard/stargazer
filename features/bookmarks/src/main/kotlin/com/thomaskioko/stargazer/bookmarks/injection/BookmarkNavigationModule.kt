package com.thomaskioko.stargazer.bookmarks.injection

import com.thomaskioko.stargazer.bookmarks.navigation.BookmarkNavigationFactory
import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface BookmarkNavigationModule {
    @Singleton
    @Binds
    @IntoSet
    fun bindBookmarkNavigationFactory(factory: BookmarkNavigationFactory): ComposeNavigationFactory
}
