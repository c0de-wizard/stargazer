package com.thomaskioko.stargazer.navigation.injection

import com.thomaskioko.stargazer.navigation.ScreenNavigationImpl
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
interface NavigationModule {

    @get:Binds
    val ScreenNavigationImpl.screenNavigatorImpl: ScreenNavigator

}