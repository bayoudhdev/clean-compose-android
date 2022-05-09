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
        navigateToPersonages = homeViewModel::navigateToPersonages,
        onRefreshQuote = homeViewModel::fetchRandomQuote,
        onErrorDismiss = homeViewModel::observeError,
        openDrawer = openDrawer,
        scaffoldState = scaffoldState,
    )
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    onRefreshQuote: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    navigateToQuotes: (Pair<String, String>) -> Unit,
    navigateToPersonages: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState
) {
    val homeListLazyListState = rememberLazyListState()
    HomeScreen(
        uiState = uiState,
        onRefreshQuote = onRefreshQuote,
        onErrorDismiss = onErrorDismiss,
        navigateToQuotes = navigateToQuotes,
        navigateToPersonages = navigateToPersonages,
        openDrawer = openDrawer,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
    )
}
