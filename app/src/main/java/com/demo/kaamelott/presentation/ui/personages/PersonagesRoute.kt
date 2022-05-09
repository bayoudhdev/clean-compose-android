package com.demo.kaamelott.presentation.ui.personages

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable

@Composable
fun PersonageRoute(
    bookID: Int? = -1,
    onClickItem: (String) -> Unit,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    PersonagesScreen(
        onClickItem = onClickItem,
        scaffoldState = scaffoldState,
        onBackPressed = onBackPressed
    )
}
