package com.aromano.bragiassignment.di

import android.content.Context
import com.aromano.bragiassignment.network.Api
import com.aromano.bragiassignment.network.KtorApi
import com.aromano.bragiassignment.presentation.core.AndroidStringProvider
import com.aromano.bragiassignment.presentation.core.StringProvider
import com.aromano.bragiassignment.network.KtorClient
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.prefs.Preferences

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
