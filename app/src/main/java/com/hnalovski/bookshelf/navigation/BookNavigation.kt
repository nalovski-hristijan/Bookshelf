package com.hnalovski.bookshelf.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hnalovski.bookshelf.screens.detail.DetailsScreen
import com.hnalovski.bookshelf.screens.detail.DetailsScreenViewModel
import com.hnalovski.bookshelf.screens.home.HomeScreen
import com.hnalovski.bookshelf.screens.home.HomeScreenViewModel

@Composable
fun BookNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = BookScreens.HomeScreen.name) {
        composable(route = BookScreens.HomeScreen.name) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navController = navController, viewModel = homeScreenViewModel)
        }

        val detailsRoute = BookScreens.DetailsScreen.name
        composable(
            route = "$detailsRoute/{bookId}",
            arguments = listOf(navArgument("bookId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments.let {
                val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                val detailsScreenViewModel = hiltViewModel<DetailsScreenViewModel>()
                DetailsScreen(
                    navController = navController,
                    detailsScreenViewModel = detailsScreenViewModel,
                    bookId = bookId
                )
            }

        }

    }
}