package com.example.helloworld.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val loader = MutableLiveData<Boolean>()
}