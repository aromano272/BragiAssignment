@file:Suppress("PropertyName")

package com.aromano.bragiassignment.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseErrorResponse(
    val success: Boolean,
    val status_code: Int,
    val status_message: String,
)