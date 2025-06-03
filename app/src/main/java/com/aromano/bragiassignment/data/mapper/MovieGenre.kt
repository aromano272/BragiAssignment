package com.aromano.bragiassignment.data.mapper

import com.aromano.bragiassignment.domain.model.MovieGenre
import com.aromano.bragiassignment.network.model.MovieGenreDto

fun MovieGenreDto.toDomain() = MovieGenre(
    id = id,
    name = name,
)