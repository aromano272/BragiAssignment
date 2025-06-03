package com.aromano.bragiassignment.data.mapper

import com.aromano.bragiassignment.domain.model.Movie
import com.aromano.bragiassignment.network.model.MovieDto
import kotlinx.datetime.LocalDate

fun MovieDto.toDomain() = Movie(
    id = id,
    backdropPath = backdrop_path,
    posterPath = poster_path,
    title = title,
    voteAverage = vote_average,
    voteCount = vote_count,
    genreIds = genre_ids,
    releaseDate = LocalDate.parse(release_date),
)