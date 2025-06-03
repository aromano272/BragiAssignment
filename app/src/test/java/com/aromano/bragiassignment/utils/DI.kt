package com.aromano.bragiassignment.utils

import com.aromano.bragiassignment.presentation.core.StringProvider
import org.koin.dsl.module

val testModule = module {
    single<StringProvider> {
        FakeStringProvider()
    }
}