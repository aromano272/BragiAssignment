package com.aromano.bragiassignment.ui.movielist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import com.aromano.bragiassignment.domain.model.Movie
import com.aromano.bragiassignment.presentation.core.CommonViewState
import com.aromano.bragiassignment.presentation.movielist.MovieListIntent
import com.aromano.bragiassignment.presentation.movielist.MovieListViewState
import com.aromano.bragiassignment.ui.core.Spacing
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    state: MovieListViewState,
    onIntent: (MovieListIntent) -> Unit,
) {
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = state.isLoading,
        onRefresh = { onIntent(MovieListIntent.RefreshClicked) }
    ) {
        if (state.fullScreenError != null) {
            Box(
                Modifier
                    .fillMaxSize()
                    // needed for the pull to refresh
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = state.fullScreenError
                )
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(Spacing.dp4),
                verticalArrangement = Arrangement.spacedBy(Spacing.dp4),
                horizontalArrangement = Arrangement.spacedBy(Spacing.dp4),
            ) {
                items(state.movies.orEmpty()) { movie ->
                    MovieGridItem(movie, onIntent)
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Spacing.dp24),
                onClick = { onIntent(MovieListIntent.FiltersClicked) },
            ) {
                Icon(
                    if (state.isGenreSelected) Icons.Default.Edit else Icons.Outlined.Edit,
                    stringResource(id = R.string.movie_list_filter_button)
                )
            }
        }
    }
}

@Composable
private fun MovieGridItem(
    movie: Movie,
    onIntent: (MovieListIntent) -> Unit,
) {
    OutlinedCard(onClick = { onIntent(MovieListIntent.MovieClicked(movie.id)) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = Spacing.dp4),
            verticalArrangement = Arrangement.spacedBy(Spacing.dp4)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(.8f),
                model = movie.posterPath ?: movie.backdropPath,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.dp8),
                text = movie.title,
                style = MaterialTheme.typography.titleSmall,
                minLines = 2,
                maxLines = 2,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.dp8),
                text = stringResource(id = R.string.movie_grid_item_rating, movie.voteAverage),
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    val movie = Movie(
        id = 1,
        backdropPath = "https://picsum.photos/200/300",
        posterPath = "https://picsum.photos/200/300",
        title = "Title 1",
        voteAverage = 4.5f,
        voteCount = 120,
        genreIds = listOf(),
        releaseDate = LocalDate(2025, 1, 1),
    )
    val movies: List<Movie> = buildList {
        repeat(10) {
            add(movie.copy(id = it))
        }
    }
    val state = MovieListViewState(
        commonState = CommonViewState(),
        isLoading = false,
        fullScreenError = null,
        isGenreSelected = false,
        movies = movies,
    )
    MovieListScreen(
        state = state,
        onIntent = {},
    )
}

@Preview
@Composable
private fun PreviewMovieGridItem() {
    val movie = Movie(
        id = 1,
        backdropPath = "https://picsum.photos/200/300",
        posterPath = "https://picsum.photos/200/300",
        title = "Title 1",
        voteAverage = 4.5f,
        voteCount = 120,
        genreIds = listOf(),
        releaseDate = LocalDate(2025, 1, 1),
    )
    MovieGridItem(
        movie = movie,
        onIntent = {},
    )
}
