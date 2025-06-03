package com.aromano.bragiassignment.domain.core

sealed interface ErrorKt {

    data class Unknown(val ex: Throwable?) : ErrorKt

    sealed interface Network : ErrorKt {
        data class Server(val message: String) : Network
        data class ServerUnknown(val ex: Exception?) : Network
        data object Unauthorized : Network
    }

}
