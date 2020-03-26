package com.example.helloworld.ui.features.main

import androidx.lifecycle.MutableLiveData
import com.example.helloworld.core.ui.BaseViewModel
import com.example.helloworld.data.main.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor( private val repository: MainRepository) : BaseViewModel() {

    val message = MutableLiveData<String>()

    fun getMessage() {
        val disposable = repository.getMessage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                message.value = it.message
            }, {
                it.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }
}