package com.example.moviedb.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.utils.LoadType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

abstract class BaseViewModel<Item>() : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorLoading = MutableLiveData<String>()
    val movies = MutableLiveData<ArrayList<Item>>()
    var listMovie = ArrayList<Item>()
    val isLoadingMore = MutableLiveData<Boolean>()

    //coroutines
    val parentJob = Job()
    val ioContext = parentJob + Dispatchers.IO
    val uiContext = parentJob + Dispatchers.Main
    val ioScope = CoroutineScope(ioContext)
    val uiScope = CoroutineScope(uiContext)

    val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun showLoading() {
        isLoading.value = true
        isLoading.postValue(true)
    }

    fun hideLoading() {
        isLoading.postValue(false)
        isLoadingMore.postValue(false)
        isLoading.value = false
        isLoadingMore.value = false
    }

    fun showLoadingMore() {
        isLoadingMore.value = true
        isLoadingMore.postValue(true)
    }

    fun showError(e: Throwable) {
        errorLoading.value = e.message
    }

    fun onDestroy() {
        compositeDisposable.clear()
        parentJob.cancel()
    }

    suspend fun onLoadSuccess(moviesResponse: List<Item>?, type: LoadType) {
        hideLoading()
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

    suspend fun onLoadFail(throwable: Throwable) {
        hideLoading()
        errorLoading.value = throwable.toString()
        withContext(uiContext){
            onLoadFail(throwable)
        }
    }
}
