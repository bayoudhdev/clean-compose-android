package com.demo.kaamelott.data.repositories

import com.demo.kaamelott.data.datasources.remote.QuotesRemoteDataSource
import com.demo.kaamelott.domain.models.QuoteDomain
import com.demo.kaamelott.domain.repositories.QuotesRepository
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(private val quotesRemoteDataSource: QuotesRemoteDataSource) :
    QuotesRepository {

    override suspend fun getRandomQuote(): Result<QuoteDomain> = quotesRemoteDataSource.getRandomQuote()

    override suspend fun getRandomQuotes(bookId: String): Result<List<QuoteDomain>> =
        quotesRemoteDataSource.getRandomQuotes(bookId)

    override suspend fun getQuotesByBookAndPersonage(
        bookId: String,
        personage: String
    ): Result<List<QuoteDomain>> =
        quotesRemoteDataSource.getQuotesByBookAndPersonage(
            bookId = bookId,
            personage = personage
        )

    override suspend fun getQuotesByPersonage(personage: String): Result<List<QuoteDomain>> =
        quotesRemoteDataSource.getQuotesByPersonage(
            personage = personage
        )
}
