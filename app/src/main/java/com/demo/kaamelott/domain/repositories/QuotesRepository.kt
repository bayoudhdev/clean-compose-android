package com.demo.kaamelott.domain.repositories

import com.demo.kaamelott.domain.models.QuoteDomain

interface QuotesRepository {

    suspend fun getRandomQuote(): Result<QuoteDomain>
}
