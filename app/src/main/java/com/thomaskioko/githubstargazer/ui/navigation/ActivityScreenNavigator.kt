package com.thomaskioko.githubstargazer.ui.navigation

import com.thomaskioko.githubstargazer.core.injection.scope.ActivityScope
import com.thomaskioko.stargazer.navigation.NavigationScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import javax.inject.Inject

@ActivityScope
class ActivityScreenNavigator @Inject constructor() : ScreenNavigator {

    var handleGoToScreen: ((NavigationScreen) -> Unit)? = null

    override fun goToScreen(navigationScreen: NavigationScreen) {
        handleGoToScreen?.invoke(navigationScreen)
    }
}