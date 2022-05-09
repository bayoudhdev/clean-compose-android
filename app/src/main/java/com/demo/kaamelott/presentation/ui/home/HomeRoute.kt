package com.demo.kaamelott.presentation.ui.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    HomeRoute(
        uiState = uiState,
        navigateToQuotes = { homeViewModel.navigateToQuotes(it.first, it.second) },
        onRefreshQuote = { homeViewModel.fetchRandomQuote() },
        onErrorDismiss = { homeViewModel.observeError(it) },
        openDrawer = openDrawer,
        scaffoldState = scaffoldState,
    )
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    navigateToQuotes: (Pair<String, String>) -> Unit,
    onRefreshQuote: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState
) {
    val homeListLazyListState = rememberLazyListState()
    HomeScreen(
        uiState = uiState,
        navigateToQuotes = navigateToQuotes,
        onRefreshQuote = onRefreshQuote,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
    )
}
