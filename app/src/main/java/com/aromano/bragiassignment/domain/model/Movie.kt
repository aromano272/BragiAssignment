package com.aromano.bragiassignment.domain.model

import kotlinx.datetime.LocalDate

typealias MovieId = Int

data class Movie(
    val id: MovieId,
    val backdropPath: String?,
    val posterPath: String?,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int,
    val genreIds: List<MovieGenreId>,
    val releaseDate: LocalDate,
)