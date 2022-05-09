package com.demo.kaamelott.data.repositories

import com.demo.kaamelott.data.datasources.QuotesDataSource
import com.demo.kaamelott.domain.models.QuoteDomain
import com.demo.kaamelott.domain.repositories.QuotesRepository
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(private val quotesDataSource: QuotesDataSource) :
    QuotesRepository {

    override suspend fun getRandomQuote(): Result<QuoteDomain> = quotesDataSource.getRandomQuote()

    override suspend fun getRandomQuotes(bookId: String): Result<List<QuoteDomain>> = quotesDataSource.getRandomQuotes(bookId)
}
