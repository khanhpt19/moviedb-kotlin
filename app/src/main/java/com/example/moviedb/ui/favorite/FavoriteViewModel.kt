package com.example.moviedb.ui.favorite

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.utils.LoadType
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    val repository: MovieRepository
) : BaseViewModel<Movie>() {
    fun loadMoviesLocal() {
//        addDisposable(
//            repository.getMoviesLocal()
//                .subscribe({
//                    onLoadSuccess(it, LoadType.NORMAL)
//                }, {
//                    onLoadFail(it)
//                })
//        )
        ioScope.async {
            try {
                val movies = repository.getMoviesLocal()
                withContext(uiContext) {
                    onLoadSuccess(movies, LoadType.NORMAL)
                }
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
    }
}
