package com.example.helloworld.base

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel(), LifecycleObserver {

    val compositeDisposable = CompositeDisposable()
    val loader = MutableLiveData<Boolean>()

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?){
        Logger.d("${event?.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(){

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}