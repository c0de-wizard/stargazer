package com.thomaskioko.stargazers.settings.injection

import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import com.thomaskioko.stargazers.settings.navigation.SettingsNavigationFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface SettingsNavigationModule {
    @Singleton
    @Binds
    @IntoSet
    fun bindSettingsNavigationFactory(factory: SettingsNavigationFactory): ComposeNavigationFactory
}
