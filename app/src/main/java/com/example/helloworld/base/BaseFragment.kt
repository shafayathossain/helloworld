package com.example.helloworld.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<ViewModel: BaseViewModel, Binding: ViewDataBinding>: DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: ViewModel
    lateinit var binding: Binding
    private lateinit var communicator: BaseFragmentCommunicator

    abstract fun getLayoutId(): Int

    abstract fun setVariables(binding: Binding)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is BaseFragmentCommunicator) {
            communicator = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(getViewModelClass())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        setVariables(binding)
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        communicator.startActivity(clz, bundle)
    }

    fun setupActionBar(toolbar: Toolbar, enableBackButton: Boolean) {
        communicator.setupActionBar(toolbar, enableBackButton)
    }

    private fun getViewModelClass(): Class<ViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]   // index of 0 means first argument of Base class param
        return type as Class<ViewModel>
    }
}