package com.example.moviedb.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.utils.LoadType
import kotlinx.coroutines.*

abstract class BaseViewModel<Item>() : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val movies = MutableLiveData<ArrayList<Item>>()
    var listMovie = ArrayList<Item>()
    val isLoadingMore = MutableLiveData<Boolean>()

    //coroutines
    val parentJob = SupervisorJob()
    val ioContext = parentJob + Dispatchers.IO
    val uiContext = parentJob + Dispatchers.Main
    val ioScope = CoroutineScope(ioContext)
    val uiScope = CoroutineScope(uiContext)

    val handler = CoroutineExceptionHandler { _, throwable ->
        uiScope.launch {
            onLoadFail(throwable)
        }
    }

    val ioScopeError = CoroutineScope(ioContext + handler)
    val uiScopeError = CoroutineScope(uiContext + handler)

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
        errorMessage.value = e.message
    }

    fun onDestroy() {
        parentJob.cancel()
    }

    suspend fun onLoadSuccess(moviesResponse: List<Item>?, type: LoadType) {
        withContext(uiContext) {
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
            movies.value = listMovie
        }
    }

    suspend fun onLoadFail(throwable: Throwable) {
        withContext(uiContext) {
            hideLoading()
            errorMessage.value = throwable.toString()
        }
    }
}
