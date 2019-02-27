package com.example.moviedb.ui.favorite

import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentFavoriteBinding
import com.example.moviedb.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    companion object {
        val TAG = "FAVORITE"
        fun newInstance() = FavoriteFragment()
    }

    override val viewModel by viewModel<FavoriteViewModel>()
    override val layoutId: Int = R.layout.fragment_favorite

}
