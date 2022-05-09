package com.demo.kaamelott.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val isConnected: Boolean
        get() = _isConnected.get()

    private var _isConnected: AtomicBoolean = AtomicBoolean(false)

    init {
        listenToConnectionChanges()
    }

    private fun listenToConnectionChanges() {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isConnected = AtomicBoolean(true)
            }

            override fun onLost(network: Network) {
                _isConnected = AtomicBoolean(false)
            }
        }

        cm.registerDefaultNetworkCallback(networkCallback)
    }
}
