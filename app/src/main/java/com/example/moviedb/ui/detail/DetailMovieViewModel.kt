package com.example.moviedb.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.model.Movie
import com.example.moviedb.ui.base.BaseViewModel

class DetailMovieViewModel : BaseViewModel<Movie>() {
    val movie = MutableLiveData<Movie>()
}
