package com.example.moviedb.ui.detail

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.example.moviedb.R
import com.example.moviedb.databinding.ActivityDetailMovieBinding
import com.example.moviedb.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding, DetailMovieViewModel>() {

    companion object {

        const val MOVIE = "MOVIE"

        fun newInstance(context: Context?, idMovie: String?): Intent {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE, idMovie)
            return intent
        }
    }

    override val viewModel by viewModel<DetailMovieViewModel>()
    override val layoutId: Int = R.layout.activity_detail_movie
    override fun initComponent(viewBinding: ViewDataBinding) {
        val id = intent.getStringExtra(MOVIE)
    }

}
