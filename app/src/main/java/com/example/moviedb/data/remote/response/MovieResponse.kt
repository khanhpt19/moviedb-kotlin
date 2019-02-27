package com.example.moviedb.data.remote.response

import com.example.moviedb.data.model.Movie
import com.google.gson.annotations.SerializedName

open class MovieResponse {
    @SerializedName("page")
    val page: Int? = null
    @SerializedName("results")
    val movies: List<Movie>? = null
    @SerializedName("total_results")
    val total_results: Int? = null
    @SerializedName("total_pages")
    val total_pages: Int? = null
}
