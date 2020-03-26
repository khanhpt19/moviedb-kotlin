package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.MovieResponse
import retrofit2.http.Path

interface MovieRepository {

    suspend fun getMovies(hashMap: HashMap<String, String> = HashMap()): MovieResponse

    suspend fun getMovie(@Path("id") id: String): Movie

    suspend fun getMoviesLocal(pageSize: Int, pageIndex: Int): List<Movie>?

    suspend fun getMovieLocal(id: String?): Movie?

    suspend fun insertMovie(movie: Movie?)

    suspend fun removeMovie(id: String?)
}
