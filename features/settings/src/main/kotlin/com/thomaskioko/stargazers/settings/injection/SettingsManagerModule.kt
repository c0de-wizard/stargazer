package com.thomaskioko.stargazers.settings.injection

import android.content.Context
import com.thomaskioko.stargazers.settings.domain.SettingsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object SettingsManagerModule {

    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context) =
        SettingsManager(context)
}
