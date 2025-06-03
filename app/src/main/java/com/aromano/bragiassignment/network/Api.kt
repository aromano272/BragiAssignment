package com.aromano.bragiassignment.network

import com.aromano.bragiassignment.domain.core.ErrorKt
import com.aromano.bragiassignment.domain.core.Outcome
import com.aromano.bragiassignment.network.model.BaseErrorResponse
import com.aromano.bragiassignment.network.model.BaseResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.reflect.typeInfo
import kotlin.coroutines.cancellation.CancellationException

interface Api {

}

class KtorApi(
    ktorClient: KtorClient
) : Api {
    private val client: HttpClient = ktorClient.client

}

private suspend inline fun <reified T> runCatchingAsOutcome(
    wrapped: Boolean = true,
    block: () -> HttpResponse,
): Outcome<T> = try {
    val response = block()
    if (wrapped) {
        val body = (response.call.bodyNullable(typeInfo<BaseResponse<T>>()) as BaseResponse<T>)
        Outcome.Success(body.data)
    } else {
        val body = response.call.bodyNullable(typeInfo<T>()) as T
        Outcome.Success(body)
    }
} catch (ex: Exception) {
    if (ex is CancellationException) throw ex
    if (ex is ClientRequestException) {
        if (ex.response.status == HttpStatusCode.Unauthorized) {
            Outcome.Failure(ErrorKt.Network.Unauthorized)
        } else {
            try {
                val errorBody = ex.response.call.bodyNullable(typeInfo<BaseErrorResponse>()) as BaseErrorResponse
                Outcome.Failure(ErrorKt.Network.Server(errorBody.message))
            } catch (errorEx: Exception) {
                Outcome.Failure(ErrorKt.Network.ServerUnknown(ex))
            }
        }
    } else {
        Outcome.Failure(ErrorKt.Network.ServerUnknown(ex))
    }
}
