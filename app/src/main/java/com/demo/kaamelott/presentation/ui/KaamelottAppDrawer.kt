package com.demo.kaamelott.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun KaamelottAppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToQuotes: () -> Unit,
    navigateToPersonages: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
    }
}
