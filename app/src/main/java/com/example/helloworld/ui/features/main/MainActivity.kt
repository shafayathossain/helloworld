package com.example.helloworld.ui.features.main

import android.os.Bundle
import com.example.helloworld.R
import com.example.helloworld.core.ui.BaseActivity
import com.example.helloworld.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id,
                MainFragment()
            )
            .commit()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setVariables(binding: ActivityMainBinding) {

    }
}
