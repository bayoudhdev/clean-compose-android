package com.demo.kaamelott.presentation.ui.personages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.demo.kaamelott.R
import com.demo.kaamelott.presentation.components.NotAvailableFeaturePopup

@Composable
fun PersonagesAppBar(
    elevation: Dp,
    onBackPressed: () -> Unit,
) {

    var showUnimplementedActionDialog by rememberSaveable { mutableStateOf(false) }
    if (showUnimplementedActionDialog) {
        NotAvailableFeaturePopup(message = R.string.functionality_not_available) {
            showUnimplementedActionDialog = false
        }
    }

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.personages_title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation,
        actions = {
            IconButton(onClick = { showUnimplementedActionDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
            }
        }
    )
}
