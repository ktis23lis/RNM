package com.example.rnm.feature.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected open lateinit var viewModel: VM

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun injectViewModel()

    protected inline fun <reified T : ViewModel> getViewModel(): T =
        ViewModelProvider(this, viewModelFactory)[T::class.java]
}