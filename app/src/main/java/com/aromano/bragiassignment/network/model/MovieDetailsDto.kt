@file:Suppress("PropertyName")

package com.aromano.bragiassignment.network.model

import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.domain.model.MovieId
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: MovieId,
    val backdrop_path: String?,
    val poster_path: String?,
    val title: String,
    val vote_average: Float,
    val vote_count: Int,
    val genre_ids: List<MovieGenreId>,
    val release_date: String,

    val budget: Int, // USD
    val revenue: Int, // USD
)