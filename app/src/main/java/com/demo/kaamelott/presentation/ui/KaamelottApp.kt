package com.demo.kaamelott.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demo.kaamelott.presentation.navigation.KaamelottDestinations
import com.demo.kaamelott.presentation.navigation.KaamelottNavGraph
import com.demo.kaamelott.presentation.navigation.KaamelottNavigationActions
import com.demo.kaamelott.presentation.theme.KaamelottTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun KaamelottApp() {
    KaamelottTheme {
        val systemUiController = rememberSystemUiController()
        val darkIcons = MaterialTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
        }

        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            KaamelottNavigationActions(navController)
        }

        val coroutineScope = rememberCoroutineScope()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: KaamelottDestinations.HOME_ROUTE

        val sizeAwareDrawerState = rememberDrawerState(DrawerValue.Closed)

        ModalDrawer(
            drawerContent = {
                KaamelottAppDrawer(
                    currentRoute = currentRoute,
                    navigateToHome = navigationActions.navigateToHome,
                    navigateToPersonages = navigationActions.navigateToPersonages,
                    navigateToQuotes = navigationActions.navigateToQuotes,
                    closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } },
                    modifier = Modifier
                        .padding(bottom = 56.dp)
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                )
            },
            drawerState = sizeAwareDrawerState,
            gesturesEnabled = true
        ) {
            KaamelottNavGraph(
                modifier = Modifier
                    .padding(bottom = 56.dp)
                    .fillMaxSize()
                    .statusBarsPadding()
                    .windowInsetsPadding(
                        WindowInsets
                            .navigationBars
                            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                    ),
                navController = navController,
                openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
            )
        }
    }
}
