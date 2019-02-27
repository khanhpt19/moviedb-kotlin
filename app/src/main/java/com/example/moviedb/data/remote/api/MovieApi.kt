package com.example.moviedb.data.remote.api

import com.example.moviedb.data.remote.response.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    fun getMoviesPopular(@Query("api_key") api_key: String): Single<MovieResponse>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: String, @Query("api_key") api_key: String): Single<MovieResponse>

}
