package com.example.moviedb.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailMovieViewModel(val repository: MovieRepository) : BaseViewModel<Movie>() {
    val movie = MutableLiveData<Movie>()
    val isFavorite = MutableLiveData<Boolean>().apply { value = false }

    fun checkFavorite(movie: Movie?) {
        addDisposable(repository.getMovieById(movie?.id).subscribe({
            if (it.id == movie?.id) {
                isFavorite.value = true
            }
        }, {
        }))

    }

    fun onFavoriteClick() {
        if (isFavorite.value == true) {
            Completable.fromAction {
                repository.removeMovie(movie.value?.id)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isFavorite.value = false
                }, {
                    onLoadFail(it)
                })
        } else {
            Completable.fromAction {
                repository.insertMovie(movie.value)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isFavorite.value = true
                }, {
                    onLoadFail(it)
                })
        }
    }
}
