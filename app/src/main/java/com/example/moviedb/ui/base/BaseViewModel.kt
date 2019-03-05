package com.example.moviedb.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.utils.LoadType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<Item>() : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorLoading = MutableLiveData<String>()
    val movies = MutableLiveData<ArrayList<Item>>()
    var listMovie = ArrayList<Item>()

    val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
    }

    fun showError(e: Throwable) {
        errorLoading.value = e.message
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }

    fun onLoadSuccess(moviesResponse: List<Item>?, type: LoadType) {
        listMovie = if (type == LoadType.MORE) {
            movies.value?:ArrayList()
        } else {
            ArrayList()
        }
        listMovie.addAll(moviesResponse ?: listOf())
        movies.value = listMovie
    }

    fun onLoadFail(throwable: Throwable) {
        errorLoading.value = throwable.toString()
    }
}
