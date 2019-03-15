package com.example.moviedb.data.repository

import com.example.moviedb.data.local.db.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.api.MovieApi
import com.example.moviedb.data.remote.response.MovieResponse
import com.example.moviedb.data.scheduler.SchedulerProvider
import com.example.moviedb.ui.more.AppExecutors
import com.example.moviedb.ui.more.RemoteDataNotFoundException
import com.example.moviedb.ui.more.Result
import io.reactivex.Single
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MovieRepositoryImpl(
    val movieAPI: MovieApi,
    val movieDao: MovieDao,
    val schedulerProvider: SchedulerProvider,
    val appExecutors: AppExecutors
) : MovieRepository {
    override suspend fun getMovie(id: String): Movie {
        return movieAPI.getMovieDetail(id).await()
    }

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

    override suspend fun getMoviesAPI(hashMap: HashMap<String, String>): Result<MovieResponse> =
        withContext(appExecutors.networkContext) {
            val request = movieAPI.getMoviesPopular(hashMap)
            try {
                val response = request.await()
                Result.Success(response)
            } catch (e: HttpException) {
                Result.Error(RemoteDataNotFoundException())
            }
        }
}
