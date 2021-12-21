package com.shafayat.helloworld.ui.features.main

import android.os.Bundle
import androidx.activity.viewModels
import com.shafayat.helloworld.R
import com.shafayat.helloworld.core.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.activity_main
}
