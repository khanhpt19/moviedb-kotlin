package com.example.moviedb.ui.home

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentHomeBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.favorite.FavoriteFragment
import com.example.moviedb.ui.more.MoreFragment
import com.example.moviedb.ui.popular.PopularFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_home

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navPopular -> {
                    swapFragments(it.itemId, it.itemId)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navFavorite -> {
                    swapFragments(it.itemId, it.itemId)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navMore -> {
                    swapFragments(it.itemId, it.itemId)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.apply {
            bottomMain.apply {
                setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
                selectedItemId = viewModel?.currentId?.value ?: R.id.navPopular
            }

        }
    }

    private fun swapFragments(@IdRes actionId: Int, key: Int) {
        if (viewModel.currentId.value != null) {
            childFragmentManager.findFragmentByTag(viewModel.currentId.value.toString())?.let {
                childFragmentManager.beginTransaction()
                    .hide(it)
                    .commit()
            }
        }

        val fragmentTransaction = childFragmentManager.beginTransaction()
        var fragment = childFragmentManager.findFragmentByTag(key.toString())

        if (fragment == null) {
            fragment = when (actionId) {

                R.id.navPopular -> PopularFragment.newInstance()

                R.id.navFavorite -> FavoriteFragment.newInstance()

                R.id.navMore -> MoreFragment.newInstance()

                else -> PopularFragment.newInstance()
            }

            fragment.let {
                fragmentTransaction.add(R.id.frameContainer, it, key.toString())
            }
        } else {
            fragmentTransaction.show(fragment)
        }

        fragmentTransaction.commit()
        viewModel.currentId.value = key
    }
}
