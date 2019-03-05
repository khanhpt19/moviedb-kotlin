package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.MovieResponse
import io.reactivex.Single

interface MovieRepository {

    fun getMoviesAPI(hashMap: HashMap<String, String> = HashMap()): Single<MovieResponse>

    fun getMoviesLocal(): Single<List<Movie>>

    fun getMovieById(id: String?): Single<Movie>

    fun insertMovie(movie: Movie?)

    fun removeMovie(id: String?)
}
