package com.demo.kaamelott.presentation.ui.quotes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.core.utils.rememberStateWithLifecycle
import com.demo.kaamelott.presentation.models.Quote
import com.demo.kaamelott.presentation.ui.home.RandomQuotes

@Composable
fun QuotesPageRoute(
    onBackPressed: () -> Unit,
    navigateToQuote: (Quote) -> Unit,
    onRefreshQuotes: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    quotesViewModel: QuotesViewModel
) {
    val uiState by rememberStateWithLifecycle(quotesViewModel.uiState)
    val quotesLazyListState = rememberLazyListState()

    QuotesScreen(
        uiState = uiState,
        onBackPressed = onBackPressed,
        scaffoldState = scaffoldState,
        onRefreshQuotes = onRefreshQuotes,
        onErrorDismiss = onErrorDismiss,
        quotesLazyListState = quotesLazyListState
    ) { hasQuotes, contentModifier ->
        QuotesList(
            modifier = contentModifier,
            state = quotesLazyListState,
            quotes = hasQuotes.quotes,
            navigateToQuote = navigateToQuote
        )
    }
}

@Composable
private fun QuotesList(
    quotes: List<Quote>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    navigateToQuote: (Quote) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = state
    ) {
        items(quotes.size) { index ->
            val quote = quotes[index]
            RandomQuotes(
                quote,
                navigateToQuote
            )
            Divider(
                modifier = Modifier.padding(horizontal = 14.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
            )
        }
    }
}

