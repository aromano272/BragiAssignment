package com.aromano.bragiassignment.data.datasourcesdef

import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.domain.model.MovieId
import com.aromano.bragiassignment.network.model.GetMovieGenresResponse
import com.aromano.bragiassignment.network.model.GetMoviesByGenreResponse
import com.aromano.bragiassignment.network.model.MovieDetailsDto

interface Api {

    suspend fun getMovieGenres(): Outcome<GetMovieGenresResponse>

    suspend fun getMoviesByGenre(genreId: MovieGenreId?): Outcome<GetMoviesByGenreResponse>

    suspend fun getMovieDetails(id: MovieId): Outcome<MovieDetailsDto>

}