package com.example.moviedb.ui.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import com.example.moviedb.utils.Constants
import kotlinx.coroutines.launch

class PopularViewModel(private val repository: MovieRepository) :
    BaseLoadMoreRefreshViewModel<Movie>() {
    val titleSearch = MutableLiveData<String>()
    val isBackFromDetail = MutableLiveData<Boolean>().apply { value = false }

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap[Constants.PAGE] = page.toString()

        viewModelScope.launch {
            try {
                if (titleSearch.value != null && titleSearch.value != "") {
                    onLoadSuccessCoroutine(
                        page,
                        repository.searchMovies(titleSearch.value.toString(), page).movies
                    )
                } else
                    onLoadSuccessCoroutine(page, repository.getMovies(hashMap).movies)
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
    }
}
