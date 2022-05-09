package com.demo.kaamelott.presentation.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object KaamelottDestinations {

    const val HOME_ROUTE = "home"
    const val PERSONAGES_ROUTE = "personages"
    const val QUOTES_ROUTE = "quotes"
}

class KaamelottNavigationActions(navController: NavHostController) {

    val navigateToHome: () -> Unit = {
        navController.navigate(KaamelottDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToQuotes: () -> Unit = {
        navController.navigate(KaamelottDestinations.QUOTES_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToPersonages: () -> Unit = {
        navController.navigate(KaamelottDestinations.PERSONAGES_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
