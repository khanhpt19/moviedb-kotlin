package com.example.moviedb.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.example.moviedb.data.model.Movie

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel<Movie>> : AppCompatActivity(),
    LifecycleOwner {
    lateinit var viewBinding: ViewBinding
    abstract val viewModel: ViewModel

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.lifecycleOwner = this
        initComponent(viewBinding)
    }

    abstract fun initComponent(viewBinding: ViewDataBinding)

}
