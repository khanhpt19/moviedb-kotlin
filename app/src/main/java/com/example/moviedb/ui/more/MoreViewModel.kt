package com.example.moviedb.ui.more

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MoreViewModel(val repository: MovieRepository) : BaseViewModel<Movie>() {
    val movieCoroutine = MutableLiveData<Movie>()
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    fun loadMovie(id: String) {
        scope.launch {
            val movie = repository.getMovie(id)
            movieCoroutine.postValue(movie)
        }
    }
}
