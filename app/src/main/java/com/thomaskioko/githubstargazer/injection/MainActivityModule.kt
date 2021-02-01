package com.thomaskioko.githubstargazer.injection

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.thomaskioko.githubstargazer.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
interface MainActivityModule {

    companion object {

        @Provides
        fun providesNavController(activity: Activity): NavController =
            activity.findNavController(R.id.nav_host_fragment)

    }

}
