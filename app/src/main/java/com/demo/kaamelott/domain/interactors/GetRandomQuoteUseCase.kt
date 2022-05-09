package com.demo.kaamelott.domain.interactors

import com.demo.kaamelott.core.bases.BaseUseCase
import com.demo.kaamelott.core.di.annotation.IoDispatcher
import com.demo.kaamelott.domain.models.extensions.toModel
import com.demo.kaamelott.domain.repositories.QuotesRepository
import com.demo.kaamelott.presentation.models.Quote
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(
    private val quotesRepository: QuotesRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, Result<Quote>>(dispatcher) {

    override suspend fun execute(parameters: Unit): Result<Quote> =
        quotesRepository.getRandomQuote().mapCatching { it.toModel() }
}
