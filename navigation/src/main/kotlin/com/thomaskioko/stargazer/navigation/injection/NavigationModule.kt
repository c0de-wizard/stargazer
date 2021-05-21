package com.thomaskioko.stargazer.navigation.injection

import com.thomaskioko.stargazer.navigation.ScreenNavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Singleton
    @Provides
    fun providesNavigationManager() = ScreenNavigationManager()
}
