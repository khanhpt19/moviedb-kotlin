package com.example.moviedb.ui.main

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.moviedb.R
import com.example.moviedb.databinding.ActivityMainBinding
import com.example.moviedb.ui.base.BaseActivity
import com.example.moviedb.ui.favorite.FavoriteFragment
import com.example.moviedb.ui.more.MoreFragment
import com.example.moviedb.ui.popular.PopularFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel by viewModel<MainViewModel>()
    override val layoutId: Int = R.layout.activity_main

    override fun initComponent(viewBinding: ViewDataBinding, savedInstanceState: Bundle?) {
        if(savedInstanceState == null)
        replaceFragment(PopularFragment.newInstance(), PopularFragment.TAG)

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_popular -> {
                    var fragment = findFragmentByTag(PopularFragment.TAG)
                    if(fragment == null){
                        fragment = PopularFragment.newInstance()
                    }
                    replaceFragment(fragment, PopularFragment.TAG)
                    true
                }
                R.id.nav_favorite
                -> {
                    replaceFragment(FavoriteFragment.newInstance(), FavoriteFragment.TAG)
                    true
                }
                R.id.nav_more
                -> {
                    var fragment = findFragmentByTag(MoreFragment.TAG)
                    if(fragment == null){
                        fragment = MoreFragment.newInstance()
                    }
                    replaceFragment(fragment, MoreFragment.TAG)
                    true
                }
                else -> false
            }
        }
    }

    fun replaceFragment(fragment: Fragment, TAG: String) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment, TAG)
            .commit()
    }
}
