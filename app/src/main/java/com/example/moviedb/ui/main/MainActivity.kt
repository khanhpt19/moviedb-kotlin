package com.example.moviedb.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.moviedb.R
import com.example.moviedb.ui.base.BaseActivity
import com.example.moviedb.ui.favorite.FavoriteFragment
import com.example.moviedb.ui.more.MoreFragment
import com.example.moviedb.ui.popular.PopularFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_popular -> {
                    replaceFragment(PopularFragment.newInstance(), PopularFragment.TAG)
                    true
                }
                R.id.nav_favorite
                -> {
                    replaceFragment(FavoriteFragment.newInstance(), FavoriteFragment.TAG)
                    true
                }
                R.id.nav_more
                -> {
                    replaceFragment(MoreFragment.newInstance(), MoreFragment.TAG)
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
