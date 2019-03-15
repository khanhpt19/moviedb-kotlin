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
    val isLoadingMore = MutableLiveData<Boolean>()

    val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
        isLoadingMore.value = false
    }

    fun showLoadingMore() {
        isLoadingMore.value = true
    }

    fun showError(e: Throwable) {
        errorLoading.value = e.message
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }

    fun onLoadSuccess(moviesResponse: List<Item>?, type: LoadType) {
        if (type == LoadType.MORE) {
            listMovie = if (movies.value != null) {
                movies.value!!
            } else {
                ArrayList()
            }
        } else if (type == LoadType.REFRESH || type == LoadType.NORMAL) {
            listMovie = ArrayList()
        }

        listMovie.addAll(moviesResponse ?: listOf())
        movies.postValue(listMovie)
    }

    fun onLoadFail(throwable: Throwable) {
        errorLoading.value = throwable.toString()
    }
}
