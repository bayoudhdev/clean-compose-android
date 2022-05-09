package com.demo.kaamelott.presentation.ui.quotes

import com.demo.kaamelott.presentation.models.ErrorMessage
import com.demo.kaamelott.presentation.models.Quote

sealed interface QuotesUiState {
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class NoQuotes(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : QuotesUiState

    data class HasQuotes(
        val quotes: List<Quote>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : QuotesUiState
}