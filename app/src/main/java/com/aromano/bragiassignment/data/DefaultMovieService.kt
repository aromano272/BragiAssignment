package com.aromano.bragiassignment.data

import com.aromano.bragiassignment.data.datasourcesdef.Api
import com.aromano.bragiassignment.data.mapper.toDomain
import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.domain.core.mapSuccess
import com.aromano.bragiassignment.domain.model.Movie
import com.aromano.bragiassignment.domain.model.MovieDetails
import com.aromano.bragiassignment.domain.model.MovieGenre
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.domain.model.MovieId
import com.aromano.bragiassignment.domain.servicesdef.MovieService
import com.aromano.bragiassignment.network.model.toDomain

class DefaultMovieService(
    private val api: Api,
) : MovieService {

    override suspend fun getMovieGenres(): Outcome<List<MovieGenre>> =
        api.getMovieGenres().mapSuccess { result ->
            result.genres.map { it.toDomain() }
        }

    // NOTE(aromano): Ignoring pagination
    override suspend fun getMoviesByGenre(genreId: MovieGenreId?): Outcome<List<Movie>> =
        api.getMoviesByGenre(genreId).mapSuccess { result ->
            result.results.map { movie ->
                movie.toDomain()
            }
        }

    override suspend fun getMovieDetails(movieId: MovieId): Outcome<MovieDetails> =
        api.getMovieDetails(movieId).mapSuccess { movie ->
            movie.toDomain()
        }

}