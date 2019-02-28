package com.example.moviedb.di

import com.example.moviedb.data.scheduler.AppSchedulerProvider
import com.example.moviedb.data.scheduler.SchedulerProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import org.koin.experimental.builder.create

val appModule = module {
    single { androidApplication().resources }
    single<SchedulerProvider> { create<AppSchedulerProvider>() }
}
