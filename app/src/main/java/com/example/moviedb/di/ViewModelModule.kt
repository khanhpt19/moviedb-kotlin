package com.example.moviedb.di

import com.example.moviedb.ui.detail.DetailMovieViewModel
import com.example.moviedb.ui.favorite.FavoriteViewModel
import com.example.moviedb.ui.home.HomeViewModel
import com.example.moviedb.ui.main.MainViewModel
import com.example.moviedb.ui.more.MoreViewModel
import com.example.moviedb.ui.popular.PopularViewModel
import com.example.moviedb.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel<SplashViewModel>()
    viewModel<HomeViewModel>()
    viewModel<MainViewModel>()
    viewModel<PopularViewModel>()
    viewModel<FavoriteViewModel>()
    viewModel<MoreViewModel>()
    viewModel<DetailMovieViewModel>()
}
