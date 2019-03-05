package com.example.moviedb.ui.popular

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.utils.Constants

class PopularViewModel(val repository: MovieRepository) : BaseViewModel<Movie>() {

    fun loadDataPopular() {
        val hashMap = HashMap<String, String>()
        hashMap.put(Constants.PAGE, "1")
        addDisposable(
            repository.getMoviesAPI(hashMap)
                .subscribe({
                    onLoadSuccess(it.movies)
                }, {
                    onLoadFail(it)
                })
        )
    }
}
