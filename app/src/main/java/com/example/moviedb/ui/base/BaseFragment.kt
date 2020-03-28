package com.example.moviedb.ui.base

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.Size
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.moviedb.BR
import com.example.moviedb.R
import com.example.moviedb.utils.Permission
import com.example.moviedb.utils.autoCleared
import com.example.moviedb.utils.showDialogLoading
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment(),
    EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    abstract val viewModel: ViewModel
    @get:LayoutRes
    abstract val layoutId: Int

    lateinit var navController: NavController

    var viewDataBinding by autoCleared<ViewBinding>()
    var loadingDialog: AlertDialog?= null

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.apply {
            setVariable(BR.viewModel, viewModel)
            root.isClickable = true
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
            navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
        }
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner, Observer {
                handleShowLoading(it == true)
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            errorMessage.observe(viewLifecycleOwner, Observer {
                handleErrorMessage(it)
            })
            navBackClick.observe(viewLifecycleOwner, Observer {
                activity?.onBackPressed()
            })
        }
    }


    open fun handleShowLoading(isLoading: Boolean) {
        if (isLoading) showLoading() else hideLoading()
    }
    fun showLoading() {
        hideLoading()
        loadingDialog = showDialogLoading()
    }

    fun hideLoading() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }
    fun handleErrorMessage(message: String) {
        toast(message)
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun navigate(actionId: Int, bundle: Bundle? = null) {
        try {
            findNavController().navigate(actionId, bundle)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    open fun navigateUp() {
        try {
            findNavController().navigateUp()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    internal fun hasPermission(@Size(min = 1) vararg permissions: String): Boolean {
        permissions.forEach {
            when (ContextCompat.checkSelfPermission(
                requireActivity(),
                it
            ) != PackageManager.PERMISSION_GRANTED) {
                true -> return false
            }
        }
        return true
    }

    internal fun requestPermission(rationale: String, @Size(min = 1) vararg permissions: String) {
        Permission.requestPermissions(
            this,
            rationale,
            PERMISSION_REQUEST_CODE,
            permissions
        )
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        super.onDestroy()
    }

    @AfterPermissionGranted(PERMISSION_REQUEST_CODE)
    open fun permissionAccepted() {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) = Unit

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) = Unit

    override fun onRationaleAccepted(requestCode: Int) = Unit

    override fun onRationaleDenied(requestCode: Int) = Unit

    companion object {
        private const val PERMISSION_REQUEST_CODE = Activity.RESULT_FIRST_USER + 1
    }
}
