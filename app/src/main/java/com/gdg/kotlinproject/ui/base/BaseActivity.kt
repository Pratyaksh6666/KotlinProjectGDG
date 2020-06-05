@file:Suppress("DEPRECATION")

package com.gdg.kotlinproject.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar

// use of abstract class which can use subclasses and functions

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel> : FragmentActivity() {

    protected lateinit var binding: B
    lateinit var viewModel: VM
    abstract fun getViewModelClass(): Class<VM>
    @LayoutRes
    protected abstract fun layoutId(): Int

    // here we are using dataBinding to connect dataObjects within XML

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId())
        viewModel = ViewModelProviders.of(this)[getViewModelClass()]
    }

    protected fun showSnackBar(msg:String){
        Snackbar.make(binding.root,msg,Snackbar.LENGTH_SHORT).show()
    }
}