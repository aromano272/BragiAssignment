package com.aromano.bragiassignment

import android.app.Application
import com.aromano.bragiassignment.di.appModule
import com.aromano.bragiassignment.di.dataModule
import com.aromano.bragiassignment.di.presentationModule
import com.aromano.bragiassignment.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule,
                dataModule,
                presentationModule,
                remoteModule,
            )
        }
    }

}