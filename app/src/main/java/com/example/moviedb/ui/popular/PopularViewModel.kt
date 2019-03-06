package com.example.moviedb.ui.popular

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.utils.Constants
import com.example.moviedb.utils.LoadType

class PopularViewModel(val repository: MovieRepository) : BaseViewModel<Movie>() {

    private var mPage: Int = 1
    private var mTotalPage: Int = 0

    fun loadDataPopular() {
        showLoading()
        val hashMap = HashMap<String, String>()
        hashMap.put(Constants.PAGE, mPage.toString())
        addDisposable(
            repository.getMoviesAPI(hashMap)
                .doAfterTerminate { hideLoading() }
                .subscribe({
                    if (it != null) {
                        onLoadSuccess(it.movies, LoadType.MORE)
                        mTotalPage = it.total_pages!!
                    }
                    hideLoading()
                }, {
                    onLoadFail(it)
                    hideLoading()
                })
        )
    }

    fun onLoadMore() {
        ++mPage
        if (mPage < mTotalPage) {
            loadDataPopular()
        }
        return
    }
}
