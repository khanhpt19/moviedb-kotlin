package com.example.moviedb.data.repository

import com.example.moviedb.data.local.db.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.api.MovieApi
import com.example.moviedb.data.remote.response.MovieResponse
import com.example.moviedb.data.scheduler.SchedulerProvider
import com.example.moviedb.ui.more.AppExecutors

class MovieRepositoryImpl(
    val movieAPI: MovieApi,
    val movieDao: MovieDao,
    val schedulerProvider: SchedulerProvider,
    val appExecutors: AppExecutors
) : MovieRepository {

    override suspend fun getMovie(id: String): Movie {
        return movieAPI.getMovieDetail(id).await()
    }

//    override suspend fun getMovie(id: String): Result<Movie> = withContext(appExecutors.networkContext) {
//        val request = movieAPI.getMovieDetail(id)
//        try {
//            val response = request.await()
//            Result.Success(response)
//        } catch (e: HttpException) {
//            Result.Error(RemoteDataNotFoundException())
//        }
//    }

    override suspend fun removeMovie(id: String?) {
        movieDao.removeMovie(id)
    }

    override suspend fun insertMovie(movie: Movie?) {
        movieDao.insert(movie)
    }

    override suspend fun getMovieById(id: String?): Movie? {
        return movieDao.getMovieById(id)
    }

    override suspend fun getMoviesLocal(): List<Movie>? {
        return movieDao.getMoviesLocal()
    }

//    override suspend fun getMoviesAPI(hashMap: HashMap<String, String>): Result<MovieResponse> =
//        withContext(appExecutors.networkContext) {
//            val request = movieAPI.getMoviesPopular(hashMap)
//            try {
//                val response = request.await()
//                Result.Success(response)
//            } catch (e: HttpException) {
//                Result.Error(RemoteDataNotFoundException())
//            }
//        }

    override suspend fun getMoviesAPI(hashMap: HashMap<String, String>): MovieResponse {
        return movieAPI.getMoviesPopular(hashMap).await()
    }
}
