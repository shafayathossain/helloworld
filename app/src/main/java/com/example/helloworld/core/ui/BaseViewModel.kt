package com.example.helloworld.core.ui

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel(), IViewModel {

    val compositeDisposable = CompositeDisposable()
    val loader = MutableLiveData<Boolean>()

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?){
        Logger.d("${event?.name}")
    }

    override fun onCreate(){

    }

    override fun onDestroy(){

    }

    override fun onStart(){

    }

    override fun onStop(){

    }

    override fun onResume(){

    }

    override fun onPause(){

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}