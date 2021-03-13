package com.thomaskioko.stargazer.core.injection.module

import android.content.Context
import com.thomaskioko.stargazer.core.network.FlowNetworkObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConnectionManagerModule {

    @Singleton
    @Provides
    fun provideFlowNetworkObserver(
        @ApplicationContext app: Context
    ) = FlowNetworkObserver(app)

}
