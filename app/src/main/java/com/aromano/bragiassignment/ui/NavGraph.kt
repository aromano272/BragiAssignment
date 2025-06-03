package com.aromano.bragiassignment.ui

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

object Nav {

    @Serializable
    object Test

}

fun NavGraphBuilder.graph(mainNavController: NavController) {

    composable<Nav.Test> {
        Text("Test")
    }

}
