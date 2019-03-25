package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.MovieResponse
import retrofit2.http.Path

interface MovieRepository {

//    suspend fun getMoviesAPI(hashMap: HashMap<String, String> = HashMap()): Result<MovieResponse>
//    suspend fun getMovie(@Path("id") id: String): Result<Movie>

    suspend fun getMoviesAPI(hashMap: HashMap<String, String> = HashMap()): MovieResponse

    suspend fun getMovie(@Path("id") id: String): Movie

    suspend fun getMoviesLocal(): List<Movie>?

    suspend fun getMovieById(id: String?): Movie?

    suspend fun insertMovie(movie: Movie?)

    suspend fun removeMovie(id: String?)
}
