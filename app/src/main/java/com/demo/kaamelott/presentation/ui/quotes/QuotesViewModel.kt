package com.demo.kaamelott.presentation.ui.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.kaamelott.R
import com.demo.kaamelott.domain.interactors.*
import com.demo.kaamelott.presentation.models.ErrorMessage
import com.demo.kaamelott.presentation.models.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private data class QuotesViewModelState(
    val quotes: List<Quote> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
) {

    fun toUiState(): QuotesUiState =
        if (quotes.isNullOrEmpty()) {
            QuotesUiState.NoQuotes(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            QuotesUiState.HasQuotes(
                errorMessages = errorMessages,
                isLoading = isLoading,
                quotes = quotes
            )
        }
}

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val getQuotesByBookAndPersonageUseCase: GetQuotesByBookAndPersonageUseCase,
    private val getQuotesByPersonageUseCase: GetQuotesByPersonageUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(QuotesViewModelState(isLoading = true))

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun fetchQuotes(
        bookId: BookId,
        personage: Personage
    ) {
        viewModelState.update { it.copy(isLoading = true) }
        if (bookId.id != "-1") {
            fetchQuotesByBookAndPersonage(
                bookId = bookId,
                personage = personage
            )
        } else {
            fetchQuotesByPersonage(personage = personage)
        }
    }

    private fun fetchQuotesByPersonage(
        personage: Personage
    ) {
        viewModelScope.launch {
            val result = getQuotesByPersonageUseCase(personage)

            viewModelState.update { state ->
                result.fold(
                    onSuccess = {
                        state.copy(
                            quotes = it.getOrNull().orEmpty(),
                            isLoading = false
                        )
                    },
                    onFailure = {
                        val errorMessages = state.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_quotes_error
                        )
                        state.copy(errorMessages = errorMessages, isLoading = false)
                    }
                )
            }
        }
    }

    private fun fetchQuotesByBookAndPersonage(
        bookId: BookId,
        personage: Personage
    ) {
        viewModelScope.launch {
            val result = getQuotesByBookAndPersonageUseCase(
                GetQuotesByBookAndPersonageUseCase.Params(
                    bookId = bookId,
                    personage = personage
                )
            )
            viewModelState.update { state ->
                result.fold(
                    onSuccess = {
                        state.copy(
                            quotes = it.getOrNull().orEmpty(),
                            isLoading = false
                        )
                    },
                    onFailure = {
                        val errorMessages = state.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_quotes_error
                        )
                        state.copy(errorMessages = errorMessages, isLoading = false)
                    }
                )
            }
        }
    }

    /**
     * Notify that an error was displayed on the screen
     */
    fun observeError(errorId: Long) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }
}