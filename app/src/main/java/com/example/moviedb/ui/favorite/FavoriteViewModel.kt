package com.example.moviedb.ui.favorite

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.utils.LoadType

class FavoriteViewModel(
    val repository: MovieRepository
) : BaseViewModel<Movie>() {
    fun loadMoviesLocal() {
        addDisposable(
            repository.getMoviesLocal()
                .subscribe({
                    onLoadSuccess(it, LoadType.NORMAL)
                }, {
                    onLoadFail(it)
                })
        )
    }
}
