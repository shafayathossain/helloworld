package com.example.helloworld.ui.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.helloworld.core.ui.BaseViewModel
import com.example.helloworld.data.main.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor( private val repository: MainRepository) : BaseViewModel() {

    val message = MutableLiveData<String>()

    // using liveData scope
    val coroutineMessage : LiveData<String> = liveData(Dispatchers.IO){
        runCatching {
            repository.getCoroutineMessage()
        }.onSuccess {
            emit(it.message)
        }.onFailure {
            it.printStackTrace()
        }
    }

    // using viewModel scope
    fun getCoroutineMessage() {
        viewModelScope.launch(Dispatchers.IO){
            runCatching {
                repository.getCoroutineMessage()
            }.onSuccess {
                message.value = it.message
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    /**
     * I think using liveDataScope works best,
     * as it reduces making any network call from
     * Activity/Fragment and the UI can be updated
     * using the layout only
     **/
}