package com.example.moviedb.ui.detail

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentDetailMovieBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.utils.setSingleClick
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding, DetailMovieViewModel>() {
    override val viewModel by viewModel<DetailMovieViewModel>()
    override val layoutId: Int = R.layout.fragment_detail_movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val movie: Movie? = arguments?.getParcelable(MOVIE)
        viewModel.movie.value = movie
        viewModel.checkFavorite(movie)
        viewDataBinding.buttonBack.setSingleClick {
            navigateUp()
        }
    }

    companion object {
        const val MOVIE = "MOVIE"

        fun getBundle(movie: Movie? = null) =
            bundleOf(MOVIE to movie)
    }
}
