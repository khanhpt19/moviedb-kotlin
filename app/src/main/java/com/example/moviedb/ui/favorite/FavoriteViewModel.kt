package com.example.moviedb.ui.favorite

import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.local.db.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: MovieRepository
) : BaseLoadMoreRefreshViewModel<Movie>() {
    override fun loadData(page: Int) {
        viewModelScope.launch {
            try {
                onLoadSuccess(page, repository.getMoviesLocal(getNumberItemPerPage(), page))
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }

    }
}
