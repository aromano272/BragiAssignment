@file:Suppress("PropertyName")

package com.aromano.bragiassignment.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GetMoviesByGenreResponse(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int,
)