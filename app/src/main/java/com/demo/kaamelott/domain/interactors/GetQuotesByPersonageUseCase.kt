package com.demo.kaamelott.domain.interactors

import android.os.Parcelable
import com.demo.kaamelott.core.bases.BaseUseCase
import com.demo.kaamelott.core.di.annotation.IoDispatcher
import com.demo.kaamelott.domain.models.extensions.toModel
import com.demo.kaamelott.domain.repositories.QuotesRepository
import com.demo.kaamelott.presentation.models.Quote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class GetQuotesByPersonageUseCase @Inject constructor(
    private val quotesRepository: QuotesRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Personage, Result<List<Quote>>>(dispatcher) {

    override suspend fun execute(parameters: Personage): Result<List<Quote>> =
        quotesRepository.getQuotesByPersonage(
            parameters.name
        ).mapCatching { it.toModel() }
}

@Parcelize
@JvmInline
value class Personage(val name: String = String()) : Parcelable
