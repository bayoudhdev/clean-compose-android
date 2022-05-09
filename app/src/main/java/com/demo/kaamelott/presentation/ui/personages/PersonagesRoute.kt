package com.demo.kaamelott.presentation.ui.personages

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable

@Composable
fun PersonageRoute(
    onClickItem: (String) -> Unit,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val personageLazyListState = rememberLazyListState()

    PersonagesScreen(
        onClickItem = onClickItem,
        scaffoldState = scaffoldState,
        onBackPressed = onBackPressed,
        personageLazyListState = personageLazyListState
    )
}
