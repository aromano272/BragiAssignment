@file:Suppress("PropertyName")

package com.aromano.bragiassignment.network.model

import com.aromano.bragiassignment.data.mapper.toDomain
import com.aromano.bragiassignment.domain.model.MovieDetails
import com.aromano.bragiassignment.domain.model.MovieId
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: MovieId,
    val backdrop_path: String?,
    val poster_path: String?,
    val title: String,
    val vote_average: Float,
    val vote_count: Int,
    val genres: List<MovieGenreDto>,
    val release_date: String,

    val budget: Int, // USD
    val revenue: Int, // USD
)

fun MovieDetailsDto.toDomain() = MovieDetails(
    id = id,
    backdropPath = backdrop_path,
    posterPath = poster_path,
    title = title,
    voteAverage = vote_average,
    voteCount = vote_count,
    genres = genres.map { it.toDomain() },
    releaseDate = LocalDate.parse(release_date),
    budget = budget,
    revenue = revenue,
)
