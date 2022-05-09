package com.demo.kaamelott.data.datasources

import com.demo.kaamelott.data.services.QuotesService
import javax.inject.Inject

class QuotesRemoteSource @Inject constructor(private val quotesService: QuotesService)
