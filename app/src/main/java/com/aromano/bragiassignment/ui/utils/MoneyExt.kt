package com.aromano.bragiassignment.ui.utils

import com.aromano.bragiassignment.domain.model.Dollars

fun Dollars.toUi() = when {
    this > 1_000_000 -> String.format("$%.1fM", this / 1_000_000f)
    this > 1_000 -> String.format("$%.1fk", this / 1_000_000f)
    else -> "$$this"
}