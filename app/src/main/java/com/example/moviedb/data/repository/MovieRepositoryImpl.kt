package com.example.moviedb.data.repository

import com.example.moviedb.data.local.db.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.api.MovieApi
import com.example.moviedb.data.remote.response.MovieResponse

class MovieRepositoryImpl(
    private val movieAPI: MovieApi,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getMovie(id: String): Movie {
        return movieAPI.getMovieDetail(id)
    }

    override suspend fun removeMovie(id: String?) {
        movieDao.removeMovie(id)
    }

    override suspend fun insertMovie(movie: Movie?) {
        movieDao.insert(movie)
    }

    override suspend fun getMovieLocal(id: String?): Movie? {
        return movieDao.getMovieById(id)
    }

    override suspend fun getMoviesLocal(pageSize: Int, pageIndex: Int): List<Movie>? {
        return movieDao.getMoviesLocal(pageSize, pageIndex)
    }

    override suspend fun getMovies(hashMap: HashMap<String, String>): MovieResponse {
        return movieAPI.getMoviesPopular(hashMap)
    }

    override suspend fun searchMovies(query: String, page: Int): MovieResponse =
        movieAPI.searchMovies(query, page)
}
