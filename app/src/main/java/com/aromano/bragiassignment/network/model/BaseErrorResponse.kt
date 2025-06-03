package com.aromano.bragiassignment.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseErrorResponse(
    val code: Int,
    val message: String,
)