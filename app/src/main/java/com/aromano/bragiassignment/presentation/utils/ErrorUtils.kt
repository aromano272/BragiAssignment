package com.aromano.bragiassignment.presentation.utils

import com.aromano.bragiassignment.R
import com.aromano.bragiassignment.domain.core.ErrorKt
import com.aromano.bragiassignment.presentation.core.StringProvider

fun ErrorKt.message(strings: StringProvider): String = when (this) {
    is ErrorKt.Unknown -> ex?.message ?: strings.getString(R.string.error_unknown)
    is ErrorKt.Network -> when (this) {
        is ErrorKt.Network.Server -> message
        is ErrorKt.Network.ServerUnknown -> ex?.message ?: strings.getString(R.string.error_unknown)
        ErrorKt.Network.Unauthorized -> strings.getString(R.string.error_network_unauthorized)
    }
}