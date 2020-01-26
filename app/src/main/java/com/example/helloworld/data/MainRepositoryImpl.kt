package com.example.helloworld.data

import io.reactivex.Single
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor() : MainRepository {

    override fun getMessage(): Single<String> {
        return Single.create {
            it.onSuccess("Hello World!!")
        }
    }
}