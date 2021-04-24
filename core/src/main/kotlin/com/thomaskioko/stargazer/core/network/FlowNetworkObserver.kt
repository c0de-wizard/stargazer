package com.thomaskioko.stargazer.core.network

import android.content.Context
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class FlowNetworkObserver @Inject constructor(
    private val context: Context
) {

    fun observeInternetConnection(): Flow<Boolean> {
        val connectedNetworks = mutableSetOf<AppNetwork>()
        return appNetworkFlow()
            .map { appNetwork ->
                if (appNetwork.isConnected) {
                    connectedNetworks += appNetwork
                } else {
                    connectedNetworks -= appNetwork
                }
                connectedNetworks.any()
            }.distinctUntilChanged()
    }


    private fun appNetworkFlow(): Flow<AppNetwork> {
        lateinit var appNetworkStream: AppNetworkStream

        @Suppress("RemoveExplicitTypeArguments")
        return channelFlow<AppNetwork> {
            appNetworkStream = AppCompatNetworkStream(this)

            appNetworkStream.subscribe(context)

            awaitClose()
        }.onCompletion {
            appNetworkStream.unsubscribe(context)
        }
    }
}
