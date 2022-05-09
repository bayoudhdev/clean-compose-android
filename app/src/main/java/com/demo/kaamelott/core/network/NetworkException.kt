package com.demo.kaamelott.core.network

import java.io.IOException

class NetworkException(message: String, cause: Throwable? = null) : IOException(message, cause)
