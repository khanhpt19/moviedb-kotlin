package com.example.moviedb.di

import android.content.Context
import androidx.room.Room
import com.example.moviedb.data.local.db.AppDatabase
import com.example.moviedb.data.local.db.dao.MovieDao
import com.example.moviedb.data.local.pref.AppPrefs
import com.example.moviedb.data.local.pref.PrefHelper
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.data.repository.MovieRepositoryImpl
import com.example.moviedb.utils.Constants
import org.koin.dsl.module.module
import org.koin.experimental.builder.create

val repositoryModule = module {
    single { create<MovieDao>() }
    single { createAppDatabase(get(), get()) }
    single<PrefHelper> { create<AppPrefs>() }
    single<MovieRepository> { create<MovieRepositoryImpl>() }
    single { createDatabaseName() }
}

fun createAppDatabase(context: Context, dbName: String) =
    Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()

fun createDatabaseName() = Constants.DATABASE_NAME
