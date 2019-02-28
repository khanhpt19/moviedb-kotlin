package com.example.moviedb.data.repository

import com.example.moviedb.data.remote.api.MovieApi
import com.example.moviedb.data.remote.response.MovieResponse
import com.example.moviedb.data.scheduler.SchedulerProvider
import io.reactivex.Single

class MovieRepositoryImpl(
    val movieAPI: MovieApi,
    val schedulerProvider: SchedulerProvider
) : MovieRepository {

    override fun getMovies(hashMap: HashMap<String, String>): Single<MovieResponse> {
        return movieAPI.getMoviesPopular(hashMap)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

}
