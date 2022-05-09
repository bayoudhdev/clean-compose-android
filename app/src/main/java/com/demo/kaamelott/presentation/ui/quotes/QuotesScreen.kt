package com.demo.kaamelott.presentation.ui.quotes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.R
import com.demo.kaamelott.core.utils.isScrolled
import com.demo.kaamelott.presentation.components.LoadingComponent
import com.demo.kaamelott.presentation.components.SnackbarHostComponent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    uiState: QuotesUiState,
    onBackPressed: () -> Unit,
    onRefreshQuotes: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    quotesLazyListState: LazyListState,
    hasQuotes: @Composable (
        uiState: QuotesUiState.HasQuotes,
        modifier: Modifier
    ) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHostComponent(hostState = it) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.quotes_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = if (!quotesLazyListState.isScrolled) 0.dp else 4.dp,
                actions = {
                    //FIXME REMOVE THIS SPACER
                    Spacer(modifier = Modifier.width(60.dp))
                }
            )
        },
        modifier = modifier
    ) { padding ->
        QuotesLoadingContentScreen(
            empty = when (uiState) {
                is QuotesUiState.HasQuotes -> false
                is QuotesUiState.NoQuotes -> uiState.isLoading
            },
            emptyContent = { LoadingComponent() },
            loading = uiState.isLoading,
            onRefresh = onRefreshQuotes,
            content = {
                QuotesContentScreen(
                    uiState = uiState,
                    modifier = Modifier.padding(padding),
                    hasQuotes = hasQuotes,
                    onRefreshQuotes = onRefreshQuotes
                )
            }
        )
    }

    HomeErrorContentScreen(
        uiState = uiState,
        onRefreshQuotes = onRefreshQuotes,
        onErrorDismiss = onErrorDismiss,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun HomeErrorContentScreen(
    uiState: QuotesUiState,
    onRefreshQuotes: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    scaffoldState: ScaffoldState,
) {
    if (uiState.errorMessages.isNotEmpty()) {
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val errorMessageText: String = stringResource(errorMessage.messageId)
        val retryMessageText = stringResource(id = R.string.try_again)

        val onRefreshQuotesState by rememberUpdatedState(onRefreshQuotes)
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        LaunchedEffect(errorMessageText, retryMessageText, scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessageText
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onRefreshQuotesState()
            }
            onErrorDismissState(errorMessage.id)
        }
    }
}

@Composable
private fun QuotesLoadingContentScreen(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

@Composable
private fun QuotesContentScreen(
    uiState: QuotesUiState,
    hasQuotes: @Composable (
        uiState: QuotesUiState.HasQuotes,
        modifier: Modifier
    ) -> Unit,
    onRefreshQuotes: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is QuotesUiState.HasQuotes -> hasQuotes(uiState, modifier)
        is QuotesUiState.NoQuotes -> {
            if (uiState.errorMessages.isEmpty()) {
                TextButton(
                    onClick = onRefreshQuotes,
                    modifier.fillMaxSize()
                ) {
                    Text(
                        stringResource(id = R.string.try_again),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Box(modifier.fillMaxSize())
            }
        }
    }
}