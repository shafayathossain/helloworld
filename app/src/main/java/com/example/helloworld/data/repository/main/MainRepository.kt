package com.example.helloworld.data.repository.main

import io.reactivex.Single

interface MainRepository {

    fun getMessage(): Single<String>
}