package com.aromano.bragiassignment.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aromano.bragiassignment.presentation.movielist.MovieListNavigation
import com.aromano.bragiassignment.presentation.movielist.MovieListViewModel
import com.aromano.bragiassignment.ui.core.Screen
import com.aromano.bragiassignment.ui.movielist.MovieListScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

object Nav {

    @Serializable
    object MovieList

}

fun NavGraphBuilder.graph(mainNavController: NavController) {

    composable<Nav.MovieList> {
        Screen(
            navController = mainNavController,
            viewModel = koinViewModel<MovieListViewModel>(),
            navigationHandler = { event ->
                when (event) {
                    is MovieListNavigation.GoToFilters -> TODO()
                    is MovieListNavigation.GoToMovieDetails -> TODO()
                }
            },
        ) { state, onIntent ->
            MovieListScreen(
                state = state,
                onIntent = onIntent,
            )
        }
    }

}
