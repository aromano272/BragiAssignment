package com.aromano.bragiassignment.network.model

import com.aromano.bragiassignment.domain.model.MovieGenreId
import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreDto(
    val id: MovieGenreId,
    val name: String,
)