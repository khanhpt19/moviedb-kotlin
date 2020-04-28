package com.example.moviedb.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: MovieRepository): BaseViewModel() {
    val movie = MutableLiveData<Movie>()
    val isFavorite = MutableLiveData<Boolean>().apply { value = false }

    fun checkFavorite(movieCheck: Movie?) {
        viewModelScope.launch {
            try {
                val favo = repository.getMovieLocal(movieCheck?.id)
                if (favo?.id == movieCheck?.id) {
                    isFavorite.value = true
                    movie.value?.isFavorite = true
                }
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
    }

    fun onFavoriteClick() {
        if (isFavorite.value == true) {
            viewModelScope.launch {
                try {
                    movie.value?.isFavorite = false
                    repository.removeMovie(movie.value?.id)
                    isFavorite.value = false
                } catch (e: Exception) {
                    onLoadFail(e)
                }

            }
        } else {
            viewModelScope.launch {
                try {
                    movie.value?.isFavorite = true
                    repository.insertMovie(movie.value)
                    isFavorite.value = true
                } catch (e: Exception) {
                    onLoadFail(e)
                }
            }
        }
    }
}
