package com.example.helloworld.ui.features.main

import android.os.Bundle
import androidx.activity.viewModels
import com.example.helloworld.R
import com.example.helloworld.core.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,
                MainFragment()
            )
            .commit()
    }

    override fun getLayoutId(): Int = R.layout.activity_main
}
