package com.example.moviedb.data.repository

import com.example.moviedb.data.remote.response.MovieResponse
import io.reactivex.Single

interface MovieRepository {

    fun getMovies(hashMap: HashMap<String, String> = HashMap()): Single<MovieResponse>

}
