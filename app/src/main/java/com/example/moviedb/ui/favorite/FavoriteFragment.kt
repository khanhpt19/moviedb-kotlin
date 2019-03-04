package com.example.moviedb.ui.favorite

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentFavoriteBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.detail.DetailMovieFragment
import com.example.moviedb.ui.popular.MovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    companion object {
        val TAG = "FAVORITE"
        fun newInstance() = FavoriteFragment()
    }

    override val viewModel by viewModel<FavoriteViewModel>()
    override val layoutId: Int = R.layout.fragment_favorite

    override fun initComponents(viewBinding: ViewDataBinding) {

        val adapter = MovieAdapter(itemClick = { goToDetail(it) })
        recycler_view_favorite.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.loadMoviesLocal()
    }

    private fun goToDetail(movie: Movie?) {
        replaceFragmentToActivity(
            DetailMovieFragment.newInstance(movie),
            R.id.container, DetailMovieFragment.TAG, true
        )
    }
}
