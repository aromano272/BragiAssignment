package com.aromano.bragiassignment.presentation.movielist

import com.aromano.bragiassignment.R
import com.aromano.bragiassignment.domain.GetMoviesByGenreUseCase
import com.aromano.bragiassignment.domain.core.doOnFailure
import com.aromano.bragiassignment.domain.core.doOnSuccess
import com.aromano.bragiassignment.presentation.core.BaseViewModel
import com.aromano.bragiassignment.presentation.core.TopBarViewState
import kotlinx.coroutines.Job


class MovieListViewModel(
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
) : BaseViewModel<
        MovieListArgs,
        MovieListIntent,
        MovieListModelState,
        MovieListViewState,
        MovieListNavigation,
        >(
    initialModelState = MovieListModelState.initial
), MovieListContract {

    init {
        loadData()
    }

    private var loadDataJob: Job? = null
    private fun loadData() {
        loadDataJob?.cancel()
        loadDataJob = launchJob {
            updateState { it.copy(isLoading = true) }
            getMoviesByGenreUseCase.execute(modelState.selectedGenreId)
                .doOnSuccess { movies ->
                    updateState { it.copy(isLoading = false, movies = movies) }
                }.doOnFailure { error ->
                    if (modelState.movies == null) {
                        updateState { it.copy(isLoading = false, error = error) }
                    } else {
                        updateState { it.copy(isLoading = false) }
                        showErrorAlert(error)
                    }
                }
        }
    }

    override fun mapViewState(state: MovieListModelState): MovieListViewState = MovieListViewState(
        commonState = state.commonState.toViewState(
            topBarViewState = TopBarViewState(
                strings.getString(R.string.movie_list_topbar_title),
                backEnabled = false,
            )
        ),
        isLoading = state.isLoading,
        fullScreenError = state.error?.message,
        isGenreSelected = state.selectedGenreId != null,
        movies = state.movies,
    )

    override suspend fun handleIntent(state: MovieListModelState, intent: MovieListIntent) {
        when (intent) {
            MovieListIntent.RefreshClicked -> loadData()
            MovieListIntent.FiltersClicked ->
                navigate(MovieListNavigation.GoToFilters(state.selectedGenreId))
            is MovieListIntent.MovieClicked ->
                navigate(MovieListNavigation.GoToMovieDetails(intent.movieId))
            is MovieListIntent.SelectedGenreChanged -> {
                if (state.selectedGenreId == intent.genreId) return
                updateState { it.copy(selectedGenreId = intent.genreId, movies = null) }
                loadData()
            }
        }
    }

}
