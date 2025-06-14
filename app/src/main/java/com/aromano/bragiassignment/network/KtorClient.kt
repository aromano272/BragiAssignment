package com.aromano.bragiassignment.network

import com.aromano.bragiassignment.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class KtorClient(
    private val json: Json,
) {
    val client = HttpClient(OkHttp) {
        expectSuccess = true

        install(Logging) {
            level = LogLevel.ALL
        }

        install(DefaultRequest) {
            url("https://api.themoviedb.org/3/")
        }

        install(ContentNegotiation) {
            json(json)
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = BuildConfig.TMDB_API_KEY,
                        refreshToken = "",
                    )
                }
            }
        }

        install(HttpRequestRetry)

        install(HttpTimeout)

    }

    companion object {
        private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
        val IMAGE_FULL_SIZE_BASE_URL = "$IMAGE_BASE_URL/original"
        val IMAGE_THUMBNAIL_BASE_URL = "$IMAGE_BASE_URL/w500"
    }
}
