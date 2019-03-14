package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.MovieResponse
import com.example.moviedb.ui.more.Result
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.Path

interface MovieRepository {

    suspend fun getMoviesAPI(hashMap: HashMap<String, String> = HashMap()): Result<MovieResponse>

    suspend fun getMovie(@Path("id") id: String): Movie

    fun getMoviesLocal(): Single<List<Movie>>

    fun getMovieById(id: String?): Single<Movie>

    fun insertMovie(movie: Movie?)

    fun removeMovie(id: String?)
}
