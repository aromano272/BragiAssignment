package com.aromano.bragiassignment.presentation.movielist

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.aromano.bragiassignment.domain.GetMoviesByGenreUseCase
import com.aromano.bragiassignment.domain.core.ErrorKt
import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.domain.model.Movie
import com.aromano.bragiassignment.domain.model.MovieGenre
import com.aromano.bragiassignment.utils.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MovieListViewModelTest : BaseTest() {

    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase = mockk {
        coEvery { execute(any()) }.returns(Outcome.Success(MOCK_MOVIES))
    }

    private val savedStateHandle = SavedStateHandle()

    private val viewModel: MovieListViewModel by lazy {
        MovieListViewModel(
            getMoviesByGenreUseCase = getMoviesByGenreUseCase,
        )
    }

    @Test
    fun `WHEN getMovies success THEN show movies`() = runTest {
        val moviesDeferred = CompletableDeferred<Outcome<List<Movie>>>()
        coEvery { getMoviesByGenreUseCase.execute(any()) }.coAnswers { moviesDeferred.await() }

        viewModel.viewStateFlow.test {
            expectMostRecentItem().run {
                assertNull(movies)
                assertTrue(isLoading)
            }

            moviesDeferred.complete(Outcome.Success(MOCK_MOVIES))

            awaitItem().run {
                assertNotNull(movies)
                assertEquals(MOCK_MOVIES, movies)
                assertFalse(isLoading)
            }
        }
    }

    @Test
    fun `WHEN getMovies failure and no movies THEN show fullscreen error`() = runTest {
        val moviesDeferred = CompletableDeferred<Outcome<List<Movie>>>()
        coEvery { getMoviesByGenreUseCase.execute(any()) }.coAnswers { moviesDeferred.await() }

        viewModel.viewStateFlow.test {
            expectMostRecentItem()
            moviesDeferred.complete(Outcome.Failure(ErrorKt.Network.Unauthorized))

            awaitItem().run {
                assertNull(movies)
                assertFalse(isLoading)
                assertNotNull(fullScreenError)
            }
        }
    }

    @Test
    fun `WHEN selected genre changes THEN fetch data`() = runTest {
        viewModel.viewStateFlow.test {
            expectMostRecentItem()

            val genreId = 7
            val newMovies = MOCK_MOVIES.map { it.copy(it.id + 100) }
            val moviesDeferred = CompletableDeferred<Outcome<List<Movie>>>()
            coEvery { getMoviesByGenreUseCase.execute(genreId) }.coAnswers { moviesDeferred.await() }

            viewModel.onIntent(MovieListIntent.SelectedGenreChanged(genreId))

            awaitItem().run {
                assertNull(movies)
                assertTrue(isGenreSelected)
            }
            awaitItem().run {
                assertTrue(isLoading)
            }

            moviesDeferred.complete(Outcome.Success(newMovies))
            coVerify { getMoviesByGenreUseCase.execute(genreId) }

            awaitItem().run {
                assertEquals(newMovies, movies)
                assertFalse(isLoading)
            }
        }
    }

    companion object {
        private val MOCK_MOVIES = listOf(
            Movie(
                id = 1,
                backdropPath = "backdropPath",
                posterPath = "posterPath",
                title = "Title 1",
                voteAverage = 4.5f,
                voteCount = 120,
                genreIds = listOf(),
                releaseDate = LocalDate(2025, 1, 1),
            ),
            Movie(
                id = 2,
                backdropPath = "backdropPath",
                posterPath = "posterPath",
                title = "Title 2",
                voteAverage = 4.5f,
                voteCount = 120,
                genreIds = listOf(),
                releaseDate = LocalDate(2025, 1, 1),
            ),
        )
    }

}