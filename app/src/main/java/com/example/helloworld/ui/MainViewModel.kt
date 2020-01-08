package com.example.helloworld.ui

import androidx.lifecycle.MutableLiveData
import com.example.helloworld.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    val message = MutableLiveData<String>()

    fun getMessage() {
        message.value = "Hello World!!"
    }
}