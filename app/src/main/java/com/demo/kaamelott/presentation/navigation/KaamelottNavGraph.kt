package com.demo.kaamelott.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.kaamelott.presentation.ui.home.HomeRoute
import com.demo.kaamelott.presentation.ui.home.HomeViewModel
import com.demo.kaamelott.presentation.ui.personages.PersonageRoute
import timber.log.Timber

@Composable
fun KaamelottNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = KaamelottDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(KaamelottDestinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeRoute(
                homeViewModel = homeViewModel,
                navigateToQuote = {},
                navigateToPersonages = {
                    navController.navigate("${KaamelottDestinations.PERSONAGES_ROUTE}/$it")
                },
                openDrawer = openDrawer
            )
        }
        composable(
            route = "${KaamelottDestinations.PERSONAGES_ROUTE}/{bookID}",
            arguments = listOf(
                navArgument("bookID") {
                    // Make argument type safe
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ) { backStackEntry ->
            val bookID = backStackEntry.arguments?.getString("bookID")?.toInt()
            PersonageRoute(
                bookID = bookID,
                onClickItem = { personage -> Timber.d("$personage----$bookID") },
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}
