package com.example.helloworld.ui.features.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.helloworld.R
import com.example.helloworld.core.ui.BaseFragment
import com.google.android.material.textview.MaterialTextView

class MainFragment : BaseFragment<MainViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.message.observe(viewLifecycleOwner, {
            view.findViewById<MaterialTextView>(R.id.tvMessage).text = it
        })

        viewModel.getMessage()
    }
}