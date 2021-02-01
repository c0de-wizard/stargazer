package com.thomaskioko.githubstargazer.injection

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.thomaskioko.githubstargazer.R
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazer.navigation.ScreenNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
interface MainActivityModule {

    @get:Binds
    val ScreenNavigatorImpl.screenNavigatorImpl: ScreenNavigator

    companion object {

        @Provides
        fun providesNavController(activity: Activity): NavController =
            activity.findNavController(R.id.nav_host_fragment)

    }

}
