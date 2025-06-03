package com.aromano.bragiassignment.domain.servicesdef

import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.domain.model.Movie
import com.aromano.bragiassignment.domain.model.MovieGenre
import com.aromano.bragiassignment.domain.model.MovieGenreId

// NOTE(aromano): Typically with UncleBob's CleanArch we'd have an intermediate data-layer model
// I've skipped that step and went for a simpler solution of mapping network directly to domain
interface MovieService {

    suspend fun getMovieGenres(): Outcome<List<MovieGenre>>

    suspend fun getMoviesByGenre(genreId: MovieGenreId?): Outcome<List<Movie>>

}