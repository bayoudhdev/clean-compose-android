package com.demo.kaamelott.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.demo.kaamelott.R
import com.demo.kaamelott.presentation.components.KaamelottIcon
import com.demo.kaamelott.presentation.components.SelectedButton
import com.demo.kaamelott.presentation.navigation.KaamelottDestinations

@Composable
fun KaamelottAppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToPersonages: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        KaamelottIcon(Modifier.padding(16.dp))
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        SelectedButton(
            icon = Icons.Filled.Home,
            label = stringResource(id = R.string.home_title),
            isSelected = currentRoute == KaamelottDestinations.HOME_ROUTE,
            action = {
                navigateToHome()
                closeDrawer()
            }
        )
        SelectedButton(
            icon = Icons.Filled.Person,
            label = stringResource(id = R.string.personages_title),
            isSelected = currentRoute == KaamelottDestinations.PERSONAGES_ROUTE,
            action = {
                navigateToPersonages()
                closeDrawer()
            }
        )
    }
}
