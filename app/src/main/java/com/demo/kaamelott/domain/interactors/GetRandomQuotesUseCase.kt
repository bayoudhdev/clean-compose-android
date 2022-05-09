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

class GetRandomQuotesUseCase @Inject constructor(
    private val quotesRepository: QuotesRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<BookId, Result<List<Quote>>>(dispatcher) {

    override suspend fun execute(parameters: BookId): Result<List<Quote>> =
        quotesRepository.getRandomQuotes(parameters.id).mapCatching { it.toModel() }
}

@Parcelize
@JvmInline
value class BookId(val id: String = String()) : Parcelable
