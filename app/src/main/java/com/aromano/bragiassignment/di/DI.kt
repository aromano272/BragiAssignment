package com.aromano.bragiassignment.di

import com.aromano.bragiassignment.network.Api
import com.aromano.bragiassignment.network.KtorApi
import com.aromano.bragiassignment.network.KtorClient
import com.aromano.bragiassignment.presentation.core.AndroidStringProvider
import com.aromano.bragiassignment.presentation.core.StringProvider
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<Json> {
        Json {
            explicitNulls = false
            encodeDefaults = true
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
    }

    single<StringProvider> {
        AndroidStringProvider(androidContext().resources)
    }

}

val presentationModule = module {

}

val dataModule = module {

}

val remoteModule = module {

    single<KtorClient> {
        KtorClient(
            json = get(),
        )
    }

    single<Api> {
        KtorApi(
            ktorClient = get(),
        )
    }

}
