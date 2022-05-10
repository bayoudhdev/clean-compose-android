package com.demo.kaamelott.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.R
import com.demo.kaamelott.core.utils.isScrolled
import com.demo.kaamelott.presentation.components.DividerComponent
import com.demo.kaamelott.presentation.components.LoadingComponent
import com.demo.kaamelott.presentation.components.RandomQuotesComponent
import com.demo.kaamelott.presentation.components.SnackbarHostComponent
import com.demo.kaamelott.presentation.models.BookSeason
import com.demo.kaamelott.presentation.models.Quote
import com.demo.kaamelott.presentation.models.getKaamelottImageId
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRefreshHome: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    navigateToQuote: (Quote) -> Unit,
    navigateToPersonages: (String) -> Unit,
    openDrawer: () -> Unit,
    homeLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    HomeScreenWithList(
        uiState = uiState,
        onRefreshHome = onRefreshHome,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        homeLazyListState = homeLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier
    ) { hasRandomQuote, contentModifier ->
        HomeList(
            modifier = contentModifier,
            state = homeLazyListState,
            quote = hasRandomQuote.randomQuote,
            navigateToQuote = navigateToQuote,
            navigateToPersonages = navigateToPersonages,
            quotes = hasRandomQuote.randomQuotes
        )
    }
}

@Composable
private fun HomeScreenWithList(
    uiState: HomeUiState,
    onRefreshHome: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    homeLazyListState: LazyListState,
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
                elevation = if (!homeLazyListState.isScrolled) 0.dp else 4.dp
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
            onRefresh = onRefreshHome,
            content = {
                HomeContentScreen(
                    uiState = uiState,
                    hasRandomQuote = hasRandomQuote,
                    onRefreshHome = onRefreshHome,
                    modifier = Modifier.padding(padding)
                )
            }
        )
    }

    HomeErrorContentScreen(
        uiState = uiState,
        onRefreshHome = onRefreshHome,
        onErrorDismiss = onErrorDismiss,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun HomeErrorContentScreen(
    uiState: HomeUiState,
    onRefreshHome: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    scaffoldState: ScaffoldState,
) {
    if (uiState.errorMessages.isNotEmpty()) {
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val errorMessageText: String = stringResource(errorMessage.messageId)
        val retryMessageText = stringResource(id = R.string.try_again)

        val onRefreshHomeState by rememberUpdatedState(onRefreshHome)
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        LaunchedEffect(errorMessageText, retryMessageText, scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessageText
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onRefreshHomeState()
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
    onRefreshHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is HomeUiState.HasRandomQuote -> hasRandomQuote(uiState, modifier)
        is HomeUiState.NoRandomQuote -> {
            if (uiState.errorMessages.isEmpty()) {
                TextButton(
                    onClick = onRefreshHome,
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
    quotes: List<Quote>,
    navigateToQuote: (Quote) -> Unit,
    navigateToPersonages: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier,
        state = state
    ) {
        item { QuoteOfDaySection(quote = quote, navigateToQuote = navigateToQuote) }
        item { BookListSection(navigateToPersonages = navigateToPersonages) }
        item { ListQuotesSection(quotes = quotes, navigateToQuote = navigateToQuote) }
    }
}

@Composable
private fun ListQuotesSection(
    quotes: List<Quote>,
    navigateToQuote: (Quote) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.home_quotes),
            style = MaterialTheme.typography.subtitle1
        )
        quotes.forEach { quote ->
            RandomQuotesComponent(
                quote,
                navigateToQuote,
            )
            DividerComponent(
                modifier = Modifier.padding(horizontal = 14.dp)
            )
        }
    }
}

@Composable
private fun QuoteOfDaySection(
    quote: Quote,
    navigateToQuote: (Quote) -> Unit
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
                navigateToQuote(
                    quote
                )
            }
        )
    )
    DividerComponent(
        modifier = Modifier.padding(horizontal = 14.dp)
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
        DividerComponent(
            modifier = Modifier.padding(horizontal = 14.dp)
        )
    }
}

@Composable
private fun BookCard(
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
