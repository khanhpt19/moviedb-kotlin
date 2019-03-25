package com.example.moviedb.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import kotlinx.coroutines.async

class DetailMovieViewModel(val repository: MovieRepository) : BaseViewModel<Movie>() {
    val movie = MutableLiveData<Movie>()
    val isFavorite = MutableLiveData<Boolean>().apply { value = false }

    fun checkFavorite(movie: Movie?) {
        ioScope.async {
            try {
                val favo = repository.getMovieById(movie?.id)
                if (favo?.id == movie?.id) {
                    isFavorite.postValue(true)
                }
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
//        addDisposable(repository.getMovieById(movie?.id).subscribe({
//            if (it.id == movie?.id) {
//                isFavorite.value = true
//            }
//        }, {
//        }))

    }

    fun onFavoriteClick() {
        if (isFavorite.value == true) {
            ioScope.async {
                try {
                    repository.removeMovie(movie.value?.id)
                    isFavorite.postValue(false)
                } catch (e: Exception) {
                    onLoadFail(e)
                }
            }
        } else {
            ioScope.async {
                try {
                    repository.insertMovie(movie.value)
                    isFavorite.postValue(true)
                } catch (e: Exception) {
                    onLoadFail(e)
                }
            }
        }
//        if (isFavorite.value == true) {
//            Completable.fromAction {
//                repository.removeMovie(movie.value?.id)
//            }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    isFavorite.value = false
//                }, {
//                    onLoadFail(it)
//                })
//        } else {
//            Completable.fromAction {
//                repository.insertMovie(movie.value)
//            }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    isFavorite.value = true
//                }, {
//                    onLoadFail(it)
//                })
//        }
    }
}