package com.aromano.bragiassignment.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
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
            url("")
        }

        install(ContentNegotiation) {
            json(json)
        }

    }
}
