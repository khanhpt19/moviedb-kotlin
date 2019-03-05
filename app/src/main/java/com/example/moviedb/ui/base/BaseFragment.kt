package com.example.moviedb.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.example.moviedb.BR
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel<Movie>> : Fragment() {
    lateinit var viewBinding: ViewBinding
    abstract val viewModel: ViewModel

    abstract val layoutId: Int

    val loadingProgress: ProgressBar? = null

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            setVariable(BR.viewModel, viewModel)
            root.isClickable = true
            setLifecycleOwner(viewLifecycleOwner)
            executePendingBindings()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            errorLoading.observe(viewLifecycleOwner, Observer {
                handleErrorMessage(it)
            })
        }
        initComponents(viewBinding)
    }

    protected open fun initComponents(viewBinding: ViewDataBinding) {}

    fun handleErrorMessage(message: String) {
        toast(message)
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun addFragment(
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false
    ) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, fragment, TAG)
            ?.apply {
                commitTransaction(this, addToBackStack)
            }
    }

    fun replaceFragment(
        fragment: Fragment,
        TAG: String?,
        addToBackStack: Boolean = false
    ) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment, TAG)
            ?.apply {
                commitTransaction(this, addToBackStack)
            }
    }

    fun replaceChildFragment(
        fragment: Fragment, container: Int, TAG: String?, addToBackStack: Boolean = false
    ) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(
            container, fragment, TAG
        )?.apply { commitTransaction(this, addToBackStack) }
    }

    fun addChildFragment(
        fragment: Fragment, container: Int, TAG: String?, addToBackStack: Boolean = false
    ) {
        activity?.supportFragmentManager?.beginTransaction()?.add(
            container, fragment, TAG
        )?.apply { commitTransaction(this, addToBackStack) }
    }


    fun findFragment(TAG: String?): Fragment? {
        return activity?.supportFragmentManager?.findFragmentByTag(TAG)
    }

    private fun commitTransaction(
        transaction: FragmentTransaction, addToBackStack: Boolean = false
    ) {
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }
}
