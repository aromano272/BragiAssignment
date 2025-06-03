package com.aromano.bragiassignment.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GetMovieGenresResponse(
    val genres: List<MovieGenreDto>,
)