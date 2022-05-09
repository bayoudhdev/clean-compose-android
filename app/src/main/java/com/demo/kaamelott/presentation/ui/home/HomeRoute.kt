package com.demo.kaamelott.presentation.ui.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.demo.kaamelott.core.utils.rememberStateWithLifecycle
import com.demo.kaamelott.presentation.models.Quote

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    navigateToQuote: (Quote) -> Unit,
    navigateToPersonages: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by rememberStateWithLifecycle(homeViewModel.uiState)
    val homeLazyListState = rememberLazyListState()

    HomeScreen(
        uiState = uiState,
        onRefreshHome = {
            //TODO WE CAN USE COMBINE FLOW
            homeViewModel.fetchRandomQuote()
            homeViewModel.fetchRandomQuotes()
        },
        onErrorDismiss = homeViewModel::observeError,
        navigateToQuote = navigateToQuote,
        navigateToPersonages = navigateToPersonages,
        openDrawer = openDrawer,
        homeLazyListState = homeLazyListState,
        scaffoldState = scaffoldState,
    )
}

