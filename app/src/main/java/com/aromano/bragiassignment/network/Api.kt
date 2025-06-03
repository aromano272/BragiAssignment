package com.aromano.bragiassignment.network

import com.aromano.bragiassignment.data.datasourcesdef.Api
import com.aromano.bragiassignment.domain.core.ErrorKt
import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.domain.core.mapSuccess
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.domain.model.MovieId
import com.aromano.bragiassignment.network.model.BaseErrorResponse
import com.aromano.bragiassignment.network.model.GetMovieGenresResponse
import com.aromano.bragiassignment.network.model.GetMoviesByGenreResponse
import com.aromano.bragiassignment.network.model.MovieDetailsDto
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.retry
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.reflect.typeInfo
import kotlin.coroutines.cancellation.CancellationException

class KtorApi(
    ktorClient: KtorClient,
) : Api {
    private val client: HttpClient = ktorClient.client

    override suspend fun getMovieGenres(): Outcome<GetMovieGenresResponse> = runCatchingAsOutcome {
        client.get("genre/movie/list")
    }

    override suspend fun getMoviesByGenre(
        genreId: MovieGenreId?,
    ): Outcome<GetMoviesByGenreResponse> = runCatchingAsOutcome<GetMoviesByGenreResponse> {
        client.get("discover/movie") {
            if (genreId != null) parameter("with_genres", genreId.toString())

            retry {
                retryOnExceptionOrServerErrors(maxRetries = 3)
                retryIf(maxRetries = 3) { _, response ->
                    response.status.value.let { it > 300 }
                }
            }
            timeout {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 30_000
                socketTimeoutMillis = 30_000
            }
        }
    }.mapSuccess { response ->
        response.copy(
            results = response.results.map { movie ->
                movie.copy(
                    backdrop_path = movie.backdrop_path?.let { KtorClient.IMAGE_THUMBNAIL_BASE_URL + it },
                    poster_path = movie.poster_path?.let { KtorClient.IMAGE_THUMBNAIL_BASE_URL + it },
                )
            }
        )
    }

    override suspend fun getMovieDetails(
        id: MovieId,
    ): Outcome<MovieDetailsDto> = runCatchingAsOutcome<MovieDetailsDto> {
        client.get("movie/${id}")
    }.mapSuccess { response ->
        response.copy(
            backdrop_path = response.backdrop_path?.let { KtorClient.IMAGE_FULL_SIZE_BASE_URL + it },
            poster_path = response.poster_path?.let { KtorClient.IMAGE_FULL_SIZE_BASE_URL + it },
        )
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

