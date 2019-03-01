package com.example.moviedb.ui.popular

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentPopularBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.ItemClickListener
import com.example.moviedb.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : BaseFragment<FragmentPopularBinding, PopularViewModel>() {

    companion object {
        val TAG = "POPULAR"
        fun newInstance() = PopularFragment()
    }

    override val viewModel by viewModel<PopularViewModel>()
    override val layoutId: Int = R.layout.fragment_popular

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = MovieAdapter()
        adapter.setOnItemClick(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                goToDetail(viewModel.movies.value?.get(position)?.id)
            }

        })
        recycler_view_popular.adapter = adapter
        viewModel.loadDataPopular()

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun goToDetail(idMovie: String?) {
        val intent = DetailMovieActivity.newInstance(context, idMovie)
        startActivity(intent)
    }
}
