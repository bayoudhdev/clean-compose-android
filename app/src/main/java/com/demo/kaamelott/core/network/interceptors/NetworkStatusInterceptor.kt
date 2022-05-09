package com.demo.kaamelott.core.network.interceptors

import com.demo.kaamelott.core.network.ConnectionManager
import com.demo.kaamelott.core.network.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkStatusInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionManager.isConnected) {
            chain.proceed(chain.request())
        } else {
            throw NetworkException("No network connection")
        }
    }
}
