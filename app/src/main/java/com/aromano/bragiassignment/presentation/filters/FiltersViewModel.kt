package com.aromano.bragiassignment.presentation.filters

import com.aromano.bragiassignment.R
import com.aromano.bragiassignment.domain.GetMovieGenresUseCase
import com.aromano.bragiassignment.domain.core.doOnFailure
import com.aromano.bragiassignment.domain.core.doOnSuccess
import com.aromano.bragiassignment.presentation.core.BaseViewModel
import com.aromano.bragiassignment.presentation.core.TopBarViewState
import kotlinx.coroutines.Job


class FiltersViewModel(
    args: FiltersArgs,
    private val getMoviesGenresUseCase: GetMovieGenresUseCase,
) : BaseViewModel<
        FiltersArgs,
        FiltersIntent,
        FiltersModelState,
        FiltersViewState,
        FiltersNavigation,
        >(
    initialModelState = FiltersModelState.initial(selectedGenreId = args.selectedGenreId)
), FiltersContract {

    init {
        loadData()
    }

    private var loadDataJob: Job? = null
    private fun loadData() {
        loadDataJob?.cancel()
        loadDataJob = launchJob {
            updateState { it.copy(isLoading = true) }
            getMoviesGenresUseCase.execute(Unit)
                .doOnSuccess { genres ->
                    updateState { it.copy(isLoading = false, genres = genres) }
                }.doOnFailure { error ->
                    if (modelState.genres == null) {
                        updateState { it.copy(isLoading = false, error = error) }
                    } else {
                        updateState { it.copy(isLoading = false) }
                        showErrorAlert(error)
                    }
                }
        }
    }

    override fun mapViewState(state: FiltersModelState): FiltersViewState = FiltersViewState(
        commonState = state.commonState.toViewState(
            topBarViewState = TopBarViewState(
                strings.getString(R.string.movie_list_topbar_title),
                backEnabled = false,
            )
        ),
        isLoading = state.isLoading,
        fullScreenError = state.error?.message,
        selectedGenreId = state.selectedGenreId,
        genres = state.genres,
    )

    override suspend fun handleIntent(state: FiltersModelState, intent: FiltersIntent) {
        when (intent) {
            FiltersIntent.RefreshClicked -> loadData()
            is FiltersIntent.GenreClicked -> navigate(FiltersNavigation.GoBackWithResult(
                intent.genreId.takeIf { it != state.selectedGenreId }
            ))
        }
    }

}
