package com.aromano.bragiassignment.ui.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.aromano.bragiassignment.domain.model.MovieGenre
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.presentation.core.CommonViewState
import com.aromano.bragiassignment.presentation.filters.FiltersIntent
import com.aromano.bragiassignment.presentation.filters.FiltersViewState
import com.aromano.bragiassignment.ui.core.Spacing
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    state: FiltersViewState,
    onIntent: (FiltersIntent) -> Unit,
) {
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = state.isLoading,
        onRefresh = { onIntent(FiltersIntent.RefreshClicked) }
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Spacing.dp4),
                verticalArrangement = Arrangement.spacedBy(Spacing.dp4),
            ) {
                items(state.genres.orEmpty()) { genre ->
                    GenreListItem(
                        genre = genre,
                        isSelected = genre.id == state.selectedGenreId,
                        onIntent = onIntent,
                    )
                }
            }
        }
    }
}

@Composable
private fun GenreListItem(
    genre: MovieGenre,
    isSelected: Boolean,
    onIntent: (FiltersIntent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FilterChip(
            selected = isSelected,
            onClick = { onIntent(FiltersIntent.GenreClicked(genre.id)) },
            label = { Text(genre.name) },

            trailingIcon = if (isSelected) {
                { Icon(Icons.Default.Close, stringResource(R.string.filters_clear_filter)) }
            } else {
                null
            },
        )
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    val movie = MovieGenre(
        id = 1,
        name = "Genre 1"
    )
    val genres: List<MovieGenre> = buildList {
        repeat(10) {
            add(movie.copy(id = it, name = "Genre $it"))
        }
    }
    val state = FiltersViewState(
        commonState = CommonViewState(),
        isLoading = false,
        fullScreenError = null,
        selectedGenreId = 2,
        genres = genres,
    )
    FiltersScreen(
        state = state,
        onIntent = {},
    )
}

@Preview
@Composable
private fun PreviewMovieGridItem() {
    val genre = MovieGenre(
        id = 1,
        name = "Genre"
    )
    GenreListItem(
        genre = genre,
        isSelected = true,
        onIntent = {},
    )
}
