package com.example.moviedb.ui.popular

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentPopularBinding
import com.example.moviedb.extension.fromView
import com.example.moviedb.extension.showSoftKeyboard
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import com.example.moviedb.ui.detail.DetailMovieFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class PopularFragment :
    BaseLoadMoreRefreshFragment<FragmentPopularBinding, PopularViewModel, Movie>() {
    override val viewModel by viewModel<PopularViewModel>()
    override val layoutId: Int = R.layout.fragment_popular
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = MoviesAdapter(itemClick = { goToDetail(it) })
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        val searchObservable = searchView.fromView(
            queryChangeAction = { query ->
                if (searchView.imeOptions == EditorInfo.IME_ACTION_DONE ||
                    searchView.imeOptions == EditorInfo.IME_ACTION_SEARCH
                ) {
                    if (query.isEmpty()) {
                        context?.showSoftKeyboard(false)
                    }
                }
            },
            querySubmitAction = {
                context?.showSoftKeyboard(false)
            }
        )
        search(searchObservable)
        viewDataBinding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
            }
        }

        viewModel.apply {
            listItem.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
            if(isBackFromDetail.value == false){
                firstLoad()
            }
            titleSearch.observe(viewLifecycleOwner, Observer {
                if(!it.isNullOrEmpty()){
                    loadData(1)
                }
            })
        }
    }

    private fun goToDetail(movie: Movie?) {
        navigate(R.id.action_homeFragment_to_detailFragment, DetailMovieFragment.getBundle(movie))
    }

    override fun onDestroy() {
        viewModel.compositeDisposable.clear()
        super.onDestroy()
    }

    private fun search(observable: Observable<String>) {
        val disposable = observable
            .debounce(300L, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
//            .filter { it.isEmpty().not() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                viewModel.titleSearch.value = t
            }
        viewModel.addDisposable(disposable)
    }

    companion object {
        fun newInstance() = PopularFragment()
    }
}
