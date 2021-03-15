package com.thomaskioko.stargazers.settings.injection

import android.content.Context
import com.thomaskioko.stargazers.settings.domain.SettingsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ActivityComponent::class)
@Module
object SettingsManagerModule {

    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context) =
        SettingsManager(context)
}
