package com.aromano.bragiassignment.utils

import com.aromano.bragiassignment.presentation.core.StringProvider

class FakeStringProvider : StringProvider {
    override fun getQuantityText(id: Int, quantity: Int): CharSequence =
        "getQuantityText(id: $id, quantity: $quantity)"

    override fun getString(id: Int): String =
        "getString(id: $id)"

    override fun getString(id: Int, vararg formatArgs: Any): String =
        "getString(id: $id, vararg formatArgs: [${formatArgs.joinToString(", ")}])"

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any): String =
        "getQuantityString(id: $id, quantity: $quantity, vararg formatArgs: [${
            formatArgs.joinToString(
                ", "
            )
        }])"

    override fun getQuantityString(id: Int, quantity: Int): String =
        "getQuantityString(id: $id, quantity: $quantity)"

    override fun getText(id: Int, def: CharSequence?): CharSequence =
        "getText(id: $id, def: $def)"
}