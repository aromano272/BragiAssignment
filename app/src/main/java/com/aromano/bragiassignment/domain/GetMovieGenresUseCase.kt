package com.aromano.bragiassignment.domain

import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.domain.core.UseCase
import com.aromano.bragiassignment.domain.model.MovieGenre
import com.aromano.bragiassignment.domain.servicesdef.MovieService

class GetMovieGenresUseCase(
    private val movieService: MovieService,
) : UseCase<Unit, List<MovieGenre>> {

    override suspend fun execute(req: Unit): Outcome<List<MovieGenre>> =
        movieService.getMovieGenres()

}