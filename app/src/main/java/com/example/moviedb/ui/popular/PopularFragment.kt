package com.example.moviedb.ui.popular

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentPopularBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.ItemClickListener
import com.example.moviedb.ui.detail.DetailMovieFragment
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : BaseFragment<FragmentPopularBinding, PopularViewModel>(), ItemClickListener {

    companion object {
        val TAG = "POPULAR"
        fun newInstance() = PopularFragment()
    }

    override val viewModel by viewModel<PopularViewModel>()
    override val layoutId: Int = R.layout.fragment_popular

    override fun initComponents(viewBinding: ViewDataBinding) {
        val adapter = MovieAdapter(this)

        recycler_view_popular.adapter = adapter
        viewModel.loadDataPopular()

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onItemClick(position: Int) {
        goToDetail(viewModel.movies.value?.get(position))
    }

    private fun goToDetail(movie: Movie?) {
        replaceFragmentToActivity(
            DetailMovieFragment.newInstance(movie),
            R.id.container, DetailMovieFragment.TAG, true
        )
    }
}
