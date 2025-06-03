package com.aromano.bragiassignment.domain

import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.domain.core.UseCase
import com.aromano.bragiassignment.domain.model.MovieDetails
import com.aromano.bragiassignment.domain.model.MovieId
import com.aromano.bragiassignment.domain.servicesdef.MovieService

class GetMovieDetailsUseCase(
    private val movieService: MovieService,
) : UseCase<MovieId, MovieDetails> {

    override suspend fun execute(req: MovieId): Outcome<MovieDetails> =
        movieService.getMovieDetails(req)

}