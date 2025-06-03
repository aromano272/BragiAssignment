package com.aromano.bragiassignment.domain

import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.domain.core.UseCase
import com.aromano.bragiassignment.domain.model.Movie
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.domain.servicesdef.MovieService

class GetMoviesByGenreUseCase(
    private val movieService: MovieService,
) : UseCase<MovieGenreId?, List<Movie>> {

    override suspend fun execute(req: MovieGenreId?): Outcome<List<Movie>> =
        movieService.getMoviesByGenre(req)

}