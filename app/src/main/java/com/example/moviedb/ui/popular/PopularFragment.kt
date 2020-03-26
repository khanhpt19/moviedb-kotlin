package com.example.moviedb.ui.popular

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import com.example.moviedb.ui.detail.DetailMovieFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, PopularViewModel, Movie>() {
    override val viewModel by viewModel<PopularViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = MoviesAdapter(itemClick = { goToDetail(it) })
        viewDataBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        viewModel.apply {
            listItem.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
            firstLoad()
        }
    }

    private fun goToDetail(movie: Movie?) {
        navigate(R.id.action_homeFragment_to_detailFragment, DetailMovieFragment.getBundle(movie))
    }

    companion object {
        fun newInstance() = PopularFragment()
    }
}
