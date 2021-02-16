package com.thomaskioko.githubstargazer.injection

import android.app.Activity
import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.thomaskioko.githubstargazer.R
import com.thomaskioko.githubstargazer.domain.SettingsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ActivityComponent::class)
@Module
object MainActivityModule {

    @Provides
    fun providesNavController(activity: Activity): NavController =
        activity.findNavController(R.id.nav_host_fragment)

    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context) =
        SettingsManager(context)
}
