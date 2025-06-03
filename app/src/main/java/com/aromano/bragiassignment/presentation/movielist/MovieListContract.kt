package com.aromano.bragiassignment.presentation.movielist

import com.aromano.bragiassignment.domain.core.ErrorKt
import com.aromano.bragiassignment.domain.model.Movie
import com.aromano.bragiassignment.domain.model.MovieGenre
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.domain.model.MovieId
import com.aromano.bragiassignment.presentation.core.Args
import com.aromano.bragiassignment.presentation.core.CommonModelState
import com.aromano.bragiassignment.presentation.core.CommonViewState
import com.aromano.bragiassignment.presentation.core.Intent
import com.aromano.bragiassignment.presentation.core.ModelStateWithCommonState
import com.aromano.bragiassignment.presentation.core.Navigation
import com.aromano.bragiassignment.presentation.core.ViewModel
import com.aromano.bragiassignment.presentation.core.ViewStateWithCommonState

interface MovieListContract : ViewModel<
        MovieListArgs,
        MovieListIntent,
        MovieListModelState,
        MovieListViewState,
        MovieListNavigation,
        >

data object MovieListArgs : Args

data class MovieListModelState(
    override val commonState: CommonModelState,
    val isLoading: Boolean,
    val error: ErrorKt?,
    val selectedGenre: MovieGenre?,
    val movies: List<Movie>?,
) : ModelStateWithCommonState<MovieListModelState> {

    override fun copyCommon(commonState: CommonModelState): MovieListModelState = copy(
        commonState = commonState,
    )

    companion object {
        val initial = MovieListModelState(
            commonState = CommonModelState(),
            isLoading = false,
            error = null,
            selectedGenre = null,
            movies = null,
        )
    }
}

data class MovieListViewState(
    override val commonState: CommonViewState,
    val isLoading: Boolean,
    val fullScreenError: String?,
    val isGenreSelected: Boolean,
    val movies: List<Movie>?,
) : ViewStateWithCommonState

sealed interface MovieListIntent : Intent {
    data object RefreshClicked : MovieListIntent
    data object FiltersClicked : MovieListIntent
    data class MovieClicked(val movieId: MovieId) : MovieListIntent
}

sealed interface MovieListNavigation : Navigation {
    data class GoToFilters(val selectedGenreId: MovieGenreId?) : MovieListNavigation
    data class GoToMovieDetails(val movieId: MovieId) : MovieListNavigation
}

