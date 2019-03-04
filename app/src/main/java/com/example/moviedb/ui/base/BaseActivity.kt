package com.example.moviedb.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.moviedb.data.model.Movie

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel<Movie>> : AppCompatActivity() {
    lateinit var viewBinding: ViewBinding
    abstract val viewModel: ViewModel

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.lifecycleOwner = this
        initComponent(viewBinding)
    }

    protected open fun initComponent(viewBinding: ViewDataBinding) {}

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
