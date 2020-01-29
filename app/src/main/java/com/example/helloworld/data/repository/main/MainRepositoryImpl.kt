package com.example.helloworld.data.repository.main

import com.example.helloworld.data.repository.main.MainRepository
import io.reactivex.Single
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor() :
    MainRepository {

    override fun getMessage(): Single<String> {
        return Single.create {
            it.onSuccess("Hello World!!")
        }
    }
}