package com.example.moviedb.ui.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel<Movie>> : AppCompatActivity() {
    lateinit var viewBinding: ViewBinding
    abstract val viewModel: ViewModel

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.lifecycleOwner = this
        initComponent(viewBinding, savedInstanceState)
    }

    protected open fun initComponent(viewBinding: ViewDataBinding, savedInstanceState: Bundle?) {}

    override fun onBackPressed() {
        navigation.visibility = View.VISIBLE
        super.onBackPressed()
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }

    fun findFragmentByTag(TAG: String?): Fragment? {
        return supportFragmentManager.findFragmentByTag(TAG)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
