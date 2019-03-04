package com.example.moviedb.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentDetailMovieBinding
import com.example.moviedb.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding, DetailMovieViewModel>() {
    companion object {

        const val TAG = "DETAIL"
        const val MOVIE = "MOVIE"

        fun newInstance(movie: Movie?) = DetailMovieFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MOVIE, movie)
            }
        }
    }

    override val viewModel by viewModel<DetailMovieViewModel>()
    override val layoutId: Int = R.layout.fragment_detail_movie

    override fun initComponents(viewBinding: ViewDataBinding) {
        val movie: Movie? = arguments?.getParcelable(MOVIE)
        viewModel.movie.value = movie
        (activity as AppCompatActivity).apply {
            supportActionBar?.apply {
                title = movie?.title
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }
}
