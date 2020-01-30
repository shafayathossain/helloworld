package com.example.helloworld.ui.features.main

import android.os.Bundle
import android.view.View
import com.example.helloworld.R
import com.example.helloworld.core.ui.BaseFragment
import com.example.helloworld.databinding.FragmentMainBinding

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun setVariables(binding: FragmentMainBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMessage()
    }
}