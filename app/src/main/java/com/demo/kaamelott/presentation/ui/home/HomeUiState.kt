package com.demo.kaamelott.presentation.ui.home

import com.demo.kaamelott.presentation.models.ErrorMessage
import com.demo.kaamelott.presentation.models.Quote

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class NoRandomQuote(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState

    data class HasRandomQuote(
        val randomQuote: Quote,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState
}
