package com.demo.kaamelott.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.kaamelott.R
import com.demo.kaamelott.domain.interactors.GetRandomQuoteUseCase
import com.demo.kaamelott.presentation.models.ErrorMessage
import com.demo.kaamelott.presentation.models.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

private data class HomeViewModelState(
    val randomQuote: Quote? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
) {

    fun toUiState(): HomeUiState =
        if (randomQuote == null) {
            HomeUiState.NoRandomQuote(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            HomeUiState.HasRandomQuote(
                errorMessages = errorMessages,
                isLoading = isLoading,
                randomQuote = randomQuote
            )
        }
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val getRandomQuoteUseCase: GetRandomQuoteUseCase) :
    ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init { fetchRandomQuote() }

    fun fetchRandomQuote() {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getRandomQuoteUseCase(Unit)
            viewModelState.update { state ->
                result.fold(
                    onSuccess = {
                        state.copy(randomQuote = it.getOrNull(), isLoading = false)
                    },
                    onFailure = {
                        val errorMessages = state.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
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

    fun navigateToQuotes(book: String, personage: String) {
    }
}
