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
    navigateToQuote: (Pair<String, String>) -> Unit,
    navigateToPersonages: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    HomeRoute(
        uiState = uiState,
        navigateToQuote = navigateToQuote,
        navigateToPersonages = navigateToPersonages,
        onRefreshHome = {
            homeViewModel.fetchRandomQuote()
            homeViewModel.fetchRandomQuotes()
        },
        onErrorDismiss = homeViewModel::observeError,
        openDrawer = openDrawer,
        scaffoldState = scaffoldState,
    )
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    onRefreshHome: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    navigateToQuote: (Pair<String, String>) -> Unit,
    navigateToPersonages: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState
) {
    val homeListLazyListState = rememberLazyListState()
    HomeScreen(
        uiState = uiState,
        onRefreshHome = onRefreshHome,
        onErrorDismiss = onErrorDismiss,
        navigateToQuote = navigateToQuote,
        navigateToPersonages = navigateToPersonages,
        openDrawer = openDrawer,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
    )
}
