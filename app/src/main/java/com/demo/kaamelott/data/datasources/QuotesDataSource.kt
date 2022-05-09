package com.demo.kaamelott.data.datasources

import com.demo.kaamelott.data.entities.extensions.toDomain
import com.demo.kaamelott.data.services.QuotesService
import com.demo.kaamelott.domain.models.QuoteDomain
import javax.inject.Inject

class QuotesDataSource @Inject constructor(private val quotesService: QuotesService) {

    suspend fun getRandomQuote(): Result<QuoteDomain> {
        val response = quotesService.getRandomQuote()
        val quote = response.body()

        return if (response.isSuccessful && quote != null) {
            Result.success(quote.toDomain())
        } else {
            Result.failure(Throwable("Can't find quote in response"))
        }
    }
}
