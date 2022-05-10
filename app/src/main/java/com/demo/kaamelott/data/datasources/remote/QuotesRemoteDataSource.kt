package com.demo.kaamelott.data.datasources.remote

import com.demo.kaamelott.data.entities.extensions.toDomain
import com.demo.kaamelott.data.services.QuotesService
import com.demo.kaamelott.domain.models.QuoteDomain
import javax.inject.Inject

class QuotesRemoteDataSource @Inject constructor(private val quotesService: QuotesService) {

    suspend fun getRandomQuote(): Result<QuoteDomain> {
        val response = quotesService.getRandomQuote()
        val quote = response.body()

        return if (response.isSuccessful && quote != null) {
            Result.success(quote.toDomain())
        } else {
            Result.failure(Throwable("Can't find quote in response"))
        }
    }

    suspend fun getRandomQuotes(bookId: String): Result<List<QuoteDomain>> {
        val response = quotesService.getAllQuotesByBook(bookId)
        val quotes = response.body()

        return if (response.isSuccessful) {
            Result.success(quotes?.quotes?.map { it.toDomain() }.orEmpty())
        } else {
            Result.failure(Throwable("Can't find quotes in response"))
        }
    }

    suspend fun getQuotesByBookAndPersonage(
        bookId: String,
        personage: String
    ): Result<List<QuoteDomain>> {
        val response = quotesService.getQuotesByBookAndPersonage(bookId, personage)
        val quotes = response.body()

        return if (response.isSuccessful) {
            Result.success(quotes?.quotes?.map { it.toDomain() }.orEmpty())
        } else {
            Result.failure(Throwable("Can't find quotes in response"))
        }
    }

    suspend fun getQuotesByPersonage(personage: String): Result<List<QuoteDomain>> {
        val response = quotesService.getQuotesByPersonage(personage)
        val quotes = response.body()

        return if (response.isSuccessful) {
            Result.success(quotes?.quotes?.map { it.toDomain() }.orEmpty())
        } else {
            Result.failure(Throwable("Can't find quotes in response"))
        }
    }
}
