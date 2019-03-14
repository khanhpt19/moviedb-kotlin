package com.example.moviedb.data.remote.api

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.MovieResponse
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MovieApi {
    @GET("movie/popular")
    fun getMoviesPopular(@QueryMap hashMap: HashMap<String, String> = HashMap()): Deferred<MovieResponse>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: String): Deferred<Movie>

}
