package com.demo.kaamelott.domain.repositories

import com.demo.kaamelott.domain.models.QuoteDomain

interface QuotesRepository {

    suspend fun getRandomQuote(): Result<QuoteDomain>
    suspend fun getRandomQuotes(bookId: String): Result<List<QuoteDomain>>
    suspend fun getQuotesByBookAndPersonage(bookId: String, personage : String): Result<List<QuoteDomain>>
    suspend fun getQuotesByPersonage(personage : String): Result<List<QuoteDomain>>
}
