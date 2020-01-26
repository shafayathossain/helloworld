package com.example.helloworld.data

import io.reactivex.Single

interface MainRepository {

    fun getMessage(): Single<String>
}