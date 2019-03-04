package com.example.moviedb.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel

class DetailMovieViewModel(val repository: MovieRepository) : BaseViewModel<Movie>() {
    val movie = MutableLiveData<Movie>()
    val isFavorite = MutableLiveData<Boolean>().apply { value = false }

    fun checkFavorite(movie: Movie?) {
        if (repository.countMovie(movie?.id) != 0) {
            isFavorite.value = true
        }
    }

    fun onFavoriteClick() {
        if (isFavorite.value == true) {
            repository.removeMovieById(movie.value?.id)
            isFavorite.value = false
        } else {
            repository.insertMovie(movie.value)
            isFavorite.value = true
        }
    }
}
