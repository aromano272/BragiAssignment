package com.aromano.bragiassignment.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val data: T
)