package com.example.helloworld.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.helloworld.R
import com.example.helloworld.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.message.observe(this, Observer {
            tvMessage.text = it
        })

        viewModel.getMessage()
    }
}