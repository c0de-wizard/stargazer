package com.thomaskioko.stargazer.injection

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.thomaskioko.stargazer.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object MainActivityModule {

    @Provides
    fun providesNavController(activity: Activity): NavController =
        activity.findNavController(R.id.nav_host_fragment)

}
