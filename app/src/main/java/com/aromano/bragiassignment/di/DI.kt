package com.aromano.bragiassignment.di

import com.aromano.bragiassignment.data.DefaultMovieService
import com.aromano.bragiassignment.data.datasourcesdef.Api
import com.aromano.bragiassignment.domain.GetMovieGenresUseCase
import com.aromano.bragiassignment.domain.GetMoviesByGenreUseCase
import com.aromano.bragiassignment.domain.model.MovieGenreId
import com.aromano.bragiassignment.domain.servicesdef.MovieService
import com.aromano.bragiassignment.network.KtorApi
import com.aromano.bragiassignment.network.KtorClient
import com.aromano.bragiassignment.presentation.core.AndroidStringProvider
import com.aromano.bragiassignment.presentation.core.StringProvider
import com.aromano.bragiassignment.presentation.filters.FiltersArgs
import com.aromano.bragiassignment.presentation.filters.FiltersViewModel
import com.aromano.bragiassignment.presentation.movielist.MovieListViewModel
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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

    viewModel {
        MovieListViewModel(
            getMoviesByGenreUseCase = get(),
        )
    }

    viewModel { (selectedGenreId: MovieGenreId?) ->
        FiltersViewModel(
            args = FiltersArgs(selectedGenreId),
            getMoviesGenresUseCase = get(),
        )
    }

}

val domainModule = module {

    single {
        GetMoviesByGenreUseCase(
            movieService = get(),
        )
    }

    single {
        GetMovieGenresUseCase(
            movieService = get(),
        )
    }

}

val dataModule = module {

    single<MovieService> {
        DefaultMovieService(
            api = get(),
        )
    }

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
