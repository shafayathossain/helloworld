package com.example.helloworld.ui

import android.os.Bundle
import com.example.helloworld.R
import com.example.helloworld.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, MainFragment())
            .commit()
    }
}
