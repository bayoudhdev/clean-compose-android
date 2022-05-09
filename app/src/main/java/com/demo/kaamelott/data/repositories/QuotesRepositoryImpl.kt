package com.demo.kaamelott.data.repositories

import com.demo.kaamelott.data.datasources.QuotesDataSource
import com.demo.kaamelott.domain.models.QuoteDomain
import com.demo.kaamelott.domain.repositories.QuotesRepository
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(private val quotesDataSource: QuotesDataSource) :
    QuotesRepository {

    override suspend fun getRandomQuote(): Result<QuoteDomain> = quotesDataSource.getRandomQuote()

    override suspend fun getRandomQuotes(bookId: String): Result<List<QuoteDomain>> =
        quotesDataSource.getRandomQuotes(bookId)

    override suspend fun getQuotesByBookAndPersonage(
        bookId: String,
        personage: String
    ): Result<List<QuoteDomain>> =
        quotesDataSource.getQuotesByBookAndPersonage(
            bookId = bookId,
            personage = personage
        )

    override suspend fun getQuotesByPersonage(personage: String): Result<List<QuoteDomain>> =
        quotesDataSource.getQuotesByPersonage(
            personage = personage
        )
}
