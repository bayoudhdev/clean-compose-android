package com.demo.kaamelott.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.R
import com.demo.kaamelott.core.utils.isScrolled
import com.demo.kaamelott.presentation.components.LoadingComponent
import com.demo.kaamelott.presentation.components.SnackbarHostComponent
import com.demo.kaamelott.presentation.models.BookSeason
import com.demo.kaamelott.presentation.models.Quote
import com.demo.kaamelott.presentation.models.getKaamelottImageId
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRefreshQuote: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    navigateToQuotes: (Pair<String, String>) -> Unit,
    navigateToPersonages: (String) -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    HomeScreenWithList(
        uiState = uiState,
        onRefreshQuote = onRefreshQuote,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier
    ) { hasRandomQuote, contentModifier ->
        HomeList(
            modifier = contentModifier,
            state = homeListLazyListState,
            quote = hasRandomQuote.randomQuote,
            navigateToQuotes = navigateToQuotes,
            navigateToPersonages = navigateToPersonages
        )
    }
}

@Composable
private fun HomeScreenWithList(
    uiState: HomeUiState,
    onRefreshQuote: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    hasRandomQuote: @Composable (
        uiState: HomeUiState.HasRandomQuote,
        modifier: Modifier
    ) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHostComponent(hostState = it) },
        topBar = {
            HomeTopAppBar(
                openDrawer = openDrawer,
                elevation = if (!homeListLazyListState.isScrolled) 0.dp else 4.dp
            )
        },
        modifier = modifier
    ) { padding ->
        HomeLoadingContentScreen(
            empty = when (uiState) {
                is HomeUiState.HasRandomQuote -> false
                is HomeUiState.NoRandomQuote -> uiState.isLoading
            },
            emptyContent = { LoadingComponent() },
            loading = uiState.isLoading,
            onRefresh = onRefreshQuote,
            content = {
                HomeContentScreen(
                    uiState = uiState,
                    hasRandomQuote = hasRandomQuote,
                    onRefreshQuote = onRefreshQuote,
                    modifier = Modifier.padding(padding)
                )
            }
        )
    }

    HomeErrorContentScreen(
        uiState = uiState,
        onRefreshQuote = onRefreshQuote,
        onErrorDismiss = onErrorDismiss,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun HomeErrorContentScreen(
    uiState: HomeUiState,
    onRefreshQuote: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    scaffoldState: ScaffoldState,
) {
    if (uiState.errorMessages.isNotEmpty()) {
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val errorMessageText: String = stringResource(errorMessage.messageId)
        val retryMessageText = stringResource(id = R.string.try_again)

        val onRefreshQuoteState by rememberUpdatedState(onRefreshQuote)
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        LaunchedEffect(errorMessageText, retryMessageText, scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessageText
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onRefreshQuoteState()
            }
            onErrorDismissState(errorMessage.id)
        }
    }
}

@Composable
private fun HomeContentScreen(
    uiState: HomeUiState,
    hasRandomQuote: @Composable (
        uiState: HomeUiState.HasRandomQuote,
        modifier: Modifier
    ) -> Unit,
    onRefreshQuote: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is HomeUiState.HasRandomQuote -> hasRandomQuote(uiState, modifier)
        is HomeUiState.NoRandomQuote -> {
            if (uiState.errorMessages.isEmpty()) {
                TextButton(
                    onClick = onRefreshQuote,
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

@Composable
private fun HomeLoadingContentScreen(
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
private fun HomeList(
    quote: Quote,
    navigateToQuotes: (Pair<String, String>) -> Unit,
    navigateToPersonages: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier,
        state = state
    ) {
        item { QuoteOfDaySection(quote = quote, navigateToQuotes = navigateToQuotes) }
        item { BookListSection(navigateToPersonages = navigateToPersonages) }
    }
}

@Composable
private fun QuoteOfDaySection(
    quote: Quote,
    navigateToQuotes: (Pair<String, String>) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = stringResource(id = R.string.home_top_item_title),
        style = MaterialTheme.typography.subtitle1,
    )
    HomeRandomQuoteCard(
        quote = quote,
        modifier = Modifier.clickable(
            onClick = {
                navigateToQuotes(
                    Pair(
                        quote.metaData.season,
                        quote.metaData.personage
                    )
                )
            }
        )
    )
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
private fun BookListSection(
    navigateToPersonages: (String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.home_books),
            style = MaterialTheme.typography.subtitle1
        )
        LazyRow(modifier = Modifier.padding(end = 16.dp)) {
            items(6) { id ->
                BookCard(
                    id.inc(),
                    navigateToPersonages,
                    Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 14.dp),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
        )
    }
}

@Composable
fun BookCard(
    id: Int,
    navigateToPersonages: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.size(200.dp, 180.dp)
    ) {
       Column(modifier = Modifier.clickable(onClick = { navigateToPersonages("$id") })) {
           Image(
               painter = painterResource(BookSeason.getByBook(id).getKaamelottImageId()),
               contentDescription = null,
               contentScale = ContentScale.FillBounds,
               modifier = Modifier
                   .height(100.dp)
                   .fillMaxWidth()
           )

           Column(modifier = Modifier.padding(16.dp)) {
               Text(
                   text = stringResource(id = R.string.home_book, id),
                   style = MaterialTheme.typography.h6,
                   maxLines = 1,
                   overflow = TextOverflow.Ellipsis
               )
               Text(
                   text = stringResource(id = R.string.home_book_go_to_personages),
                   maxLines = 1,
                   overflow = TextOverflow.Ellipsis,
                   style = MaterialTheme.typography.body2
               )
           }
       }
    }
}
