package com.aromano.bragiassignment.presentation.moviedetails

import com.aromano.bragiassignment.domain.GetMovieDetailsUseCase
import com.aromano.bragiassignment.domain.core.doOnFailure
import com.aromano.bragiassignment.domain.core.doOnSuccess
import com.aromano.bragiassignment.presentation.core.BaseViewModel
import com.aromano.bragiassignment.presentation.core.TopBarViewState
import kotlinx.coroutines.Job


class MovieDetailsViewModel(
    args: MovieDetailsArgs,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
) : BaseViewModel<
        MovieDetailsArgs,
        MovieDetailsIntent,
        MovieDetailsModelState,
        MovieDetailsViewState,
        MovieDetailsNavigation,
        >(
    initialModelState = MovieDetailsModelState.initial(movieId = args.movieId)
), MovieDetailsContract {

    init {
        loadData()
    }

    private var loadDataJob: Job? = null
    private fun loadData() {
        loadDataJob?.cancel()
        loadDataJob = launchJob {
            updateState { it.copy(isLoading = true) }
            getMovieDetailsUseCase.execute(modelState.movieId)
                .doOnSuccess { details ->
                    updateState { it.copy(isLoading = false, details = details) }
                }.doOnFailure { error ->
                    if (modelState.details == null) {
                        updateState { it.copy(isLoading = false, error = error) }
                    } else {
                        updateState { it.copy(isLoading = false) }
                        showErrorAlert(error)
                    }
                }
        }
    }

    override fun mapViewState(state: MovieDetailsModelState): MovieDetailsViewState =
        MovieDetailsViewState(
            commonState = state.commonState.toViewState(
                topBarViewState = TopBarViewState(
                    state.details?.title.orEmpty(),
                    backEnabled = true,
                )
            ),
            isLoading = state.isLoading,
            fullScreenError = state.error?.message,
            details = state.details,
        )

    override suspend fun handleIntent(state: MovieDetailsModelState, intent: MovieDetailsIntent) {
        when (intent) {
            MovieDetailsIntent.RefreshClicked -> loadData()
        }
    }

}
