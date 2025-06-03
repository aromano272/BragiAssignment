package com.aromano.bragiassignment.ui.moviedetails

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.aromano.bragiassignment.R
import com.aromano.bragiassignment.domain.model.MovieDetails
import com.aromano.bragiassignment.domain.model.MovieGenre
import com.aromano.bragiassignment.presentation.core.CommonViewState
import com.aromano.bragiassignment.presentation.moviedetails.MovieDetailsIntent
import com.aromano.bragiassignment.presentation.moviedetails.MovieDetailsViewState
import com.aromano.bragiassignment.ui.core.Spacing
import com.aromano.bragiassignment.ui.utils.toUi
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    state: MovieDetailsViewState,
    onIntent: (MovieDetailsIntent) -> Unit,
) {
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = state.isLoading,
        onRefresh = { onIntent(MovieDetailsIntent.RefreshClicked) }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val details = state.details
            if (state.fullScreenError != null) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = state.fullScreenError
                )
            } else if (details != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = Spacing.dp32),
                    verticalArrangement = Arrangement.spacedBy(Spacing.dp8),
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = details.posterPath ?: details.backdropPath,
                        contentDescription = details.title,
                        contentScale = ContentScale.Crop,
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.dp16),
                        text = details.title,
                        style = MaterialTheme.typography.titleLarge,
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.dp16),
                        text = stringResource(R.string.movie_details_budget, details.budget.toUi()),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.dp16),
                        text = stringResource(
                            R.string.movie_details_revenue,
                            details.revenue.toUi(),
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.dp16),
                        text = stringResource(
                            R.string.movie_details_release_date,
                            details.releaseDate,
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.dp16)
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.dp8),
                    ) {
                        details.genres.forEach { genre ->
                            AssistChip(
                                onClick = {},
                                label = { Text(genre.name) },
                            )
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    val genre = MovieGenre(
        id = 1,
        name = "Genre 1"
    )
    val genres: List<MovieGenre> = buildList {
        repeat(10) {
            add(genre.copy(id = it, name = "Genre $it"))
        }
    }
    val movie = MovieDetails(
        id = 1,
        backdropPath = "https://picsum.photos/200/300",
        posterPath = "https://picsum.photos/200/300",
        title = "Title 1",
        voteAverage = 4.5f,
        voteCount = 120,
        genres = genres,
        releaseDate = LocalDate(2025, 1, 1),
        budget = 1_500_000,
        revenue = 2_700_000,
    )
    val state = MovieDetailsViewState(
        commonState = CommonViewState(),
        isLoading = false,
        fullScreenError = null,
        details = movie,
    )
    MovieDetailsScreen(
        state = state,
        onIntent = {},
    )
}
