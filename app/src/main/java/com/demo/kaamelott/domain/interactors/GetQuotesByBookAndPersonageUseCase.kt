package com.demo.kaamelott.domain.interactors

import com.demo.kaamelott.core.bases.BaseUseCase
import com.demo.kaamelott.core.di.annotation.IoDispatcher
import com.demo.kaamelott.domain.models.extensions.toModel
import com.demo.kaamelott.domain.repositories.QuotesRepository
import com.demo.kaamelott.presentation.models.Quote
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetQuotesByBookAndPersonageUseCase @Inject constructor(
    private val quotesRepository: QuotesRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<GetQuotesByBookAndPersonageUseCase.Params, Result<List<Quote>>>(dispatcher) {

    override suspend fun execute(parameters: Params): Result<List<Quote>> =
        quotesRepository.getQuotesByBookAndPersonage(
            parameters.bookId.id,
            parameters.personage.name
        ).mapCatching { it.toModel() }

    data class Params(
        val bookId: BookId,
        val personage: Personage
    )
}

