package com.aromano.bragiassignment.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val id: MovieId,
    val backdropPath: String?,
    val posterPath: String?,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int,
    val genreIds: List<MovieGenreId>,
    val releaseDate: String,

    val budget: Dollars,
    val revenue: Dollars,
)