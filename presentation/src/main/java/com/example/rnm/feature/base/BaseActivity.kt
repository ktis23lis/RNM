package com.example.rnm.feature.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected open lateinit var viewModel: VM

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun injectViewModel()

    protected inline fun <reified T : ViewModel> getViewModel(): T =
        ViewModelProvider(this, viewModelFactory)[T::class.java]

}