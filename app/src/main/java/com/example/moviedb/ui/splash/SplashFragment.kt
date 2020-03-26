package com.example.moviedb.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentSplashBinding
import com.example.moviedb.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_splash
    private val handler = Handler()
    private lateinit var task: Runnable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task = Runnable {
            navController.navigate(R.id.action_splashFragment_to_homeFragment)
        }
        handler.postDelayed(task, TIME_DELAY)
    }

    companion object {
        private const val TIME_DELAY = 500L
    }
}
