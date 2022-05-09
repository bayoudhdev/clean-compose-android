package com.demo.kaamelott.presentation.ui.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import timber.log.Timber

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    HomeScreenWithList(
        uiState = uiState,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier
    ) { _, contentModifier ->
    }
}

@Composable
private fun HomeScreenWithList(
    uiState: HomeUiState,
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
    Timber.d(uiState.toString())
    Surface(color = Color.Green) {
    }
}
