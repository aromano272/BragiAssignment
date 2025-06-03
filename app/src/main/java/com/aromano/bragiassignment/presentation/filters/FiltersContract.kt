package com.aromano.bragiassignment.presentation.filters

import com.aromano.bragiassignment.domain.core.ErrorKt
import com.aromano.bragiassignment.domain.model.MovieGenre
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.presentation.core.Args
import com.aromano.bragiassignment.presentation.core.CommonModelState
import com.aromano.bragiassignment.presentation.core.CommonViewState
import com.aromano.bragiassignment.presentation.core.Intent
import com.aromano.bragiassignment.presentation.core.ModelStateWithCommonState
import com.aromano.bragiassignment.presentation.core.Navigation
import com.aromano.bragiassignment.presentation.core.ViewModel
import com.aromano.bragiassignment.presentation.core.ViewStateWithCommonState

interface FiltersContract : ViewModel<
        FiltersArgs,
        FiltersIntent,
        FiltersModelState,
        FiltersViewState,
        FiltersNavigation,
        >

data class FiltersArgs(
    val selectedGenreId: MovieGenreId?,
) : Args

data class FiltersModelState(
    override val commonState: CommonModelState,
    val isLoading: Boolean,
    val error: ErrorKt?,
    val selectedGenreId: MovieGenreId?,
    val genres: List<MovieGenre>?,
) : ModelStateWithCommonState<FiltersModelState> {

    override fun copyCommon(commonState: CommonModelState): FiltersModelState = copy(
        commonState = commonState,
    )

    companion object {
        fun initial(selectedGenreId: MovieGenreId?) = FiltersModelState(
            commonState = CommonModelState(),
            isLoading = false,
            error = null,
            selectedGenreId = selectedGenreId,
            genres = null,
        )
    }
}

data class FiltersViewState(
    override val commonState: CommonViewState,
    val isLoading: Boolean,
    val fullScreenError: String?,
    val selectedGenreId: MovieGenreId?,
    val genres: List<MovieGenre>?,
) : ViewStateWithCommonState

sealed interface FiltersIntent : Intent {
    data object RefreshClicked : FiltersIntent
    data class GenreClicked(val genreId: MovieGenreId) : FiltersIntent
}

sealed interface FiltersNavigation : Navigation {
    data class GoBackWithResult(val selectedGenreId: MovieGenreId?) : FiltersNavigation
}

