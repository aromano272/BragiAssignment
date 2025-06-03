package com.aromano.bragiassignment.ui

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.presentation.filters.FiltersNavigation
import com.aromano.bragiassignment.presentation.filters.FiltersViewModel
import com.aromano.bragiassignment.presentation.movielist.MovieListIntent
import com.aromano.bragiassignment.presentation.movielist.MovieListNavigation
import com.aromano.bragiassignment.presentation.movielist.MovieListViewModel
import com.aromano.bragiassignment.ui.Nav.Filters.Companion.FILTERS_RESULT_KEY
import com.aromano.bragiassignment.ui.core.Screen
import com.aromano.bragiassignment.ui.filters.FiltersScreen
import com.aromano.bragiassignment.ui.movielist.MovieListScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.defaultExtras
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

object Nav {

    @Serializable
    object MovieList

    @Serializable
    data class Filters(val selectedGenreId: MovieGenreId?) {
        companion object {
            val FILTERS_RESULT_KEY = "Filters.result"
        }
    }

}

fun NavGraphBuilder.graph(mainNavController: NavController) {

    composable<Nav.MovieList> { backStackEntry ->
        val viewModel = koinViewModel<MovieListViewModel>()
        LaunchedEffect(Unit) {
            backStackEntry.savedStateHandle
                .getStateFlow<MovieGenreId?>(FILTERS_RESULT_KEY, null)
                .collect {
                    viewModel.onIntent(MovieListIntent.SelectedGenreChanged(it))
                }
        }
        Screen(
            navController = mainNavController,
            viewModel = viewModel,
            navigationHandler = { event ->
                when (event) {
                    is MovieListNavigation.GoToFilters -> navigate(Nav.Filters(event.selectedGenreId))
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

    composable<Nav.Filters> { backStackEntry ->
        val selectedGenreId = backStackEntry.toRoute<Nav.Filters>().selectedGenreId
        Screen(
            navController = mainNavController,
            viewModel = koinViewModel<FiltersViewModel> {
                parametersOf(selectedGenreId)
            },
            navigationHandler = { event ->
                when (event) {
                    is FiltersNavigation.GoBackWithResult -> previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(FILTERS_RESULT_KEY, event.selectedGenreId)
                        .also { navigateUp() }
                }
            },
        ) { state, onIntent ->
            FiltersScreen(
                state = state,
                onIntent = onIntent,
            )
        }
    }

}
