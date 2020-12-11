package com.thomaskioko.githubstargazer.injection

import com.thomaskioko.githubstargazer.core.injection.scope.ActivityScope
import com.thomaskioko.githubstargazer.core.viewmodel.getComponent
import com.thomaskioko.githubstargazer.ui.MainActivity
import com.thomaskioko.githubstargazer.ui.navigation.ActivityScreenNavigator
import com.thomaskioko.stargazer.navigation.NavigationDependencies
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import dagger.Binds
import dagger.Component
import dagger.Module

@ActivityScope
@Component(modules = [MainActivityModule::class])
interface MainActivityComponent : NavigationDependencies {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(): MainActivityComponent
    }
}

@Module
interface MainActivityModule {

    @Binds
    fun bindScreenNavigator(activityScreenNavigator: ActivityScreenNavigator): ScreenNavigator
}

fun MainActivity.injectAndGetComponent(): MainActivityComponent {
    val component = getComponent { DaggerMainActivityComponent.create() }
    component.inject(this)
    return component
}
