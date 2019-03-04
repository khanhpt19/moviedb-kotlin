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
    override fun countMovie(id: String?): Int{
         return movieDao.countMovie(id)
    }

    override fun removeMovieById(id: String?){
        return movieDao.removeMovieById(id)
    }

    override fun insertMovie(movie: Movie?) {
        return movieDao.insert(movie)
    }

    override fun getMovieLocal(id: String): Single<Movie> {
        return movieDao.getMovieById(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    override fun getMoviesLocal(): Single<List<Movie>> {
        return movieDao.getMovies()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    override fun getMovies(hashMap: HashMap<String, String>): Single<MovieResponse> {
        return movieAPI.getMoviesPopular(hashMap)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

}
