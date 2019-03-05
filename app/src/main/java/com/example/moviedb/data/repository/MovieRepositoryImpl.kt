package com.example.moviedb.data.repository

import com.example.moviedb.data.local.db.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.api.MovieApi
import com.example.moviedb.data.remote.response.MovieResponse
import com.example.moviedb.data.scheduler.SchedulerProvider
import io.reactivex.Single

class MovieRepositoryImpl(
    val movieAPI: MovieApi,
    val movieDao: MovieDao,
    val schedulerProvider: SchedulerProvider
) : MovieRepository {

    override fun removeMovie(id: String?) {
        movieDao.removeMovie(id)
    }

    override fun insertMovie(movie: Movie?) {
        movieDao.insert(movie)
    }

    override fun getMovieById(id: String?): Single<Movie> {
        return movieDao.getMovieById(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    override fun getMoviesLocal(): Single<List<Movie>> {
        return movieDao.getMoviesLocal()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    override fun getMoviesAPI(hashMap: HashMap<String, String>): Single<MovieResponse> {
        return movieAPI.getMoviesPopular(hashMap)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

}
