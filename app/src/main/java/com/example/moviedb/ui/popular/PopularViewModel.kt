package com.example.moviedb.ui.popular

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.utils.Constants
import com.example.moviedb.utils.LoadType
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PopularViewModel(val repository: MovieRepository) : BaseViewModel<Movie>() {

    private var mPage: Int = 1
    private var mTotalPage: Int = 0

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

        ioScope.async {
            try {
                val movieResponse = repository.getMoviesAPI(hashMap)
                withContext(uiContext) {
                    onLoadSuccess(movieResponse.movies, type)
                }
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
    }

    fun onLoadMore() {
        ++mPage
        loadDataPopular(LoadType.MORE)
    }
}
