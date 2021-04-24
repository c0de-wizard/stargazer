package com.thomaskioko.stargazer.core.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.launch


internal sealed class AppNetwork(val isConnected: Boolean) {
    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
}

@Suppress("DEPRECATION")
internal class AppCompatNetwork(
    private val networkInfo: NetworkInfo
) : AppNetwork(networkInfo.isConnected) {

    override fun equals(other: Any?) =
        other is AppCompatNetwork &&
                other.networkInfo.type ==
                networkInfo.type &&
                other.networkInfo.subtype == networkInfo.subtype

    override fun hashCode() =
        31 * networkInfo.type.hashCode() + networkInfo.subtype.hashCode()
}

internal interface AppNetworkStream {
    val producerScope: ProducerScope<AppNetwork>
    fun subscribe(context: Context)
    fun unsubscribe(context: Context)
}

@Suppress("DEPRECATION")
internal class AppCompatNetworkStream(
    override val producerScope: ProducerScope<AppNetwork>
) : BroadcastReceiver(), AppNetworkStream {

    override fun subscribe(context: Context) {
        context.registerReceiver(
            this,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
            ?.let { producerScope.apply { launch { send(AppCompatNetwork(it)) } } }
    }

    override fun unsubscribe(context: Context) = context.unregisterReceiver(this)
}
