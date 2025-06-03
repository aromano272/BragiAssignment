package com.aromano.bragiassignment.domain.model

import kotlinx.datetime.LocalDate

data class MovieDetails(
    val id: MovieId,
    val backdropPath: String?,
    val posterPath: String?,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int,
    val genres: List<MovieGenre>,
    val releaseDate: LocalDate,

    val budget: Dollars,
    val revenue: Dollars,
)