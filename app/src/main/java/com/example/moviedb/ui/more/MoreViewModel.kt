package com.example.moviedb.ui.more

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MoreViewModel(val repository: MovieRepository) : BaseViewModel<Movie>() {
    val movieCoroutine = MutableLiveData<Movie>()

    fun loadMovie(id: String) {
        ioScope.async {
            try {
                val movieResponse = repository.getMovie(id)
                withContext(uiContext){
                    movieCoroutine.value = movieResponse
                }
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
    }
}
