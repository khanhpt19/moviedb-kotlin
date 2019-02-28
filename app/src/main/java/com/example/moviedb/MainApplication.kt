package com.example.moviedb

import android.app.Application
import com.example.moviedb.di.appModule
import com.example.moviedb.di.networkModule
import com.example.moviedb.di.repositoryModule
import com.example.moviedb.di.viewModelModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            this, listOf(
                appModule,
                networkModule,
                repositoryModule,
                viewModelModule
            )
        )
    }
}
