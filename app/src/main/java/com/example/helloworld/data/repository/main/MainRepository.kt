package com.example.helloworld.data.repository.main

import com.example.helloworld.data.model.Message
import io.reactivex.Flowable
import io.reactivex.Single

interface MainRepository {

    fun getMessage(): Flowable<Message>
}