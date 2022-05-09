package com.demo.kaamelott.presentation.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.kaamelott.domain.interactors.BookId
import com.demo.kaamelott.domain.interactors.Personage
import com.demo.kaamelott.presentation.components.QuotePopupDialog
import com.demo.kaamelott.presentation.models.Quote
import com.demo.kaamelott.presentation.ui.home.HomeRoute
import com.demo.kaamelott.presentation.ui.home.HomeViewModel
import com.demo.kaamelott.presentation.ui.personages.PersonageRoute
import com.demo.kaamelott.presentation.ui.quotes.QuotesPageRoute
import com.demo.kaamelott.presentation.ui.quotes.QuotesViewModel

@Composable
fun KaamelottNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = KaamelottDestinations.HOME_ROUTE
) {
    var openDialog by rememberSaveable { mutableStateOf(Pair<Boolean, Quote?>(false, null)) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(KaamelottDestinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel = hiltViewModel()

            HomeRoute(
                homeViewModel = homeViewModel,
                navigateToQuote = {
                    openDialog = Pair(true, it)
                },
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
                onClickItem = { personage -> navController.navigate("${KaamelottDestinations.QUOTES_ROUTE}/$bookID/$personage") },
                onBackPressed = navController::popBackStack
            )
        }
        composable(
            route = "${KaamelottDestinations.QUOTES_ROUTE}/{bookID}/{personage}",
            arguments = listOf(
                navArgument("bookID") {
                    // Make argument type safe
                    type = NavType.StringType
                    defaultValue = "-1"
                },
                navArgument("personage") {
                    // Make argument type safe
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->

            val quotesViewModel: QuotesViewModel = hiltViewModel()
            val bookId = backStackEntry.arguments?.getString("bookID").orEmpty()
            val personage = backStackEntry.arguments?.getString("personage").orEmpty()


            quotesViewModel.fetchQuotes(
                BookId(bookId),
                Personage(personage)
            )

            QuotesPageRoute(
                quotesViewModel = quotesViewModel,
                onBackPressed = navController::popBackStack,
                navigateToQuote = {
                    openDialog = Pair(true, it)
                },
                onRefreshQuotes = {
                    quotesViewModel.fetchQuotes(
                        BookId(bookId),
                        Personage(personage)
                    )
                },
                onErrorDismiss = quotesViewModel::observeError
            )
        }
    }

    if (openDialog.first && openDialog.second != null) {
        QuotePopupDialog(
            quote = openDialog.second
        ) {
            openDialog = Pair(false, null)
        }
    }
}
