package com.aromano.bragiassignment.presentation.moviedetails

import com.aromano.bragiassignment.domain.core.ErrorKt
import com.aromano.bragiassignment.domain.model.MovieDetails
import com.aromano.bragiassignment.domain.model.MovieId
import com.aromano.bragiassignment.presentation.core.Args
import com.aromano.bragiassignment.presentation.core.CommonModelState
import com.aromano.bragiassignment.presentation.core.CommonViewState
import com.aromano.bragiassignment.presentation.core.Intent
import com.aromano.bragiassignment.presentation.core.ModelStateWithCommonState
import com.aromano.bragiassignment.presentation.core.Navigation
import com.aromano.bragiassignment.presentation.core.ViewModel
import com.aromano.bragiassignment.presentation.core.ViewStateWithCommonState

interface MovieDetailsContract : ViewModel<
        MovieDetailsArgs,
        MovieDetailsIntent,
        MovieDetailsModelState,
        MovieDetailsViewState,
        MovieDetailsNavigation,
        >

data class MovieDetailsArgs(
    val movieId: MovieId,
) : Args

data class MovieDetailsModelState(
    override val commonState: CommonModelState,
    val isLoading: Boolean,
    val error: ErrorKt?,
    val movieId: MovieId,
    val details: MovieDetails?,
) : ModelStateWithCommonState<MovieDetailsModelState> {

    override fun copyCommon(commonState: CommonModelState): MovieDetailsModelState = copy(
        commonState = commonState,
    )

    companion object {
        fun initial(movieId: MovieId) = MovieDetailsModelState(
            commonState = CommonModelState(),
            isLoading = false,
            error = null,
            movieId = movieId,
            details = null,
        )
    }
}

data class MovieDetailsViewState(
    override val commonState: CommonViewState,
    val isLoading: Boolean,
    val fullScreenError: String?,
    val details: MovieDetails?,
) : ViewStateWithCommonState

sealed interface MovieDetailsIntent : Intent {
    data object RefreshClicked : MovieDetailsIntent
}

sealed interface MovieDetailsNavigation : Navigation {
}

