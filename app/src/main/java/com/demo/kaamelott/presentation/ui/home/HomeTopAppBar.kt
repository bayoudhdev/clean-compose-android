package com.demo.kaamelott.presentation.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.R
import com.demo.kaamelott.presentation.components.KaamelottIcon
import com.demo.kaamelott.presentation.components.NotAvailableFeaturePopupDialog

@Composable
fun HomeTopAppBar(
    elevation: Dp,
    openDrawer: () -> Unit
) {
    var showUnimplementedActionDialog by rememberSaveable { mutableStateOf(false) }
    if (showUnimplementedActionDialog) {
        NotAvailableFeaturePopupDialog(message = R.string.functionality_not_available) {
            showUnimplementedActionDialog = false
        }
    }

    TopAppBar(
        title = {
            KaamelottIcon(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentWidth()
                    .padding(bottom = 4.dp, top = 8.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        actions = {
            IconButton(onClick = { showUnimplementedActionDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation
    )
}
