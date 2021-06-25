package com.thomaskioko.stargazers.settings.injection

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.thomaskioko.stargazers.settings.domain.StargazerPreferences
import com.thomaskioko.stargazers.settings.domain.StargazerPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SettingsManagerModule {

    @Named("app")
    @Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}

@InstallIn(SingletonComponent::class)
@Module
internal abstract class SettingsModuleBinds {
    @Singleton
    @Binds
    abstract fun providePreferences(bind: StargazerPreferencesImpl): StargazerPreferences
}
