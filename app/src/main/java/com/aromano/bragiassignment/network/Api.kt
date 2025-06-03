package com.aromano.bragiassignment.network

import com.aromano.bragiassignment.domain.core.ErrorKt
import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.domain.model.MovieId
import com.aromano.bragiassignment.network.model.BaseErrorResponse
import com.aromano.bragiassignment.network.model.GetMoviesByGenreResponse
import com.aromano.bragiassignment.network.model.MovieDetailsDto
import com.aromano.bragiassignment.network.model.MovieGenreDto
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.reflect.typeInfo
import kotlin.coroutines.cancellation.CancellationException

interface Api {

    suspend fun getMovieGenres(): Outcome<List<MovieGenreDto>>

    suspend fun getMoviesByGenre(genreId: MovieGenreId): Outcome<GetMoviesByGenreResponse>

    suspend fun getMovieDetails(id: MovieId): Outcome<MovieDetailsDto>

}

class KtorApi(
    ktorClient: KtorClient,
) : Api {
    private val client: HttpClient = ktorClient.client

    override suspend fun getMovieGenres(): Outcome<List<MovieGenreDto>> = runCatchingAsOutcome {
        client.get("genre/movie/list")
    }

    override suspend fun getMoviesByGenre(
        genreId: MovieGenreId,
    ): Outcome<GetMoviesByGenreResponse> = runCatchingAsOutcome {
        client.get("discover/movie") {
            parameter("with_genres", genreId.toString())
        }
    }

    override suspend fun getMovieDetails(
        id: MovieId,
    ): Outcome<MovieDetailsDto> = runCatchingAsOutcome {
        client.get("movie/${id}")
    }

}

private suspend inline fun <reified T> runCatchingAsOutcome(
    block: () -> HttpResponse,
): Outcome<T> = try {
    val response = block()
    val body = response.call.bodyNullable(typeInfo<T>()) as T
    Outcome.Success(body)
} catch (ex: Exception) {
    if (ex is CancellationException) throw ex
    if (ex is ClientRequestException) {
        if (ex.response.status == HttpStatusCode.Unauthorized) {
            Outcome.Failure(ErrorKt.Network.Unauthorized)
        } else {
            try {
                val errorBody =
                    ex.response.call.bodyNullable(typeInfo<BaseErrorResponse>()) as BaseErrorResponse
                Outcome.Failure(ErrorKt.Network.Server(errorBody.status_message))
            } catch (errorEx: Exception) {
                Outcome.Failure(ErrorKt.Network.ServerUnknown(ex))
            }
        }
    } else {
        Outcome.Failure(ErrorKt.Network.ServerUnknown(ex))
    }
}
