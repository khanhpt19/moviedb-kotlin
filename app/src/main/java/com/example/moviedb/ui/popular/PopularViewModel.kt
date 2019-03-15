package com.example.moviedb.ui.popular

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.ui.more.Result
import com.example.moviedb.utils.Constants
import com.example.moviedb.utils.LoadType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

class PopularViewModel(val repository: MovieRepository) : BaseViewModel<Movie>() {

    private var mPage: Int = 1
    private var mTotalPage: Int = 0

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    fun loadDataPopular(type: LoadType) {
        if (type == LoadType.NORMAL) {
            showLoading()
        } else if (type == LoadType.MORE) {
            showLoadingMore()
        } else if (type == LoadType.REFRESH) {
            hideLoading()
            mPage = 1
        }
        val hashMap = HashMap<String, String>()
        hashMap[Constants.PAGE] = mPage.toString()

        scope.async {
            isLoading.postValue(false)
            isLoadingMore.postValue(false)
            val movieResponse = repository.getMoviesAPI(hashMap)
            if (movieResponse is Result.Success) {
                onLoadSuccess(movieResponse.data.movies, type)
            }
        }
    }

    fun onLoadMore() {
        ++mPage
        loadDataPopular(LoadType.MORE)
    }
}
