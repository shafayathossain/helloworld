package com.shafayat.helloworld.data.main

import com.shafayat.helloworld.data.main.model.Message
import io.reactivex.Maybe

interface MainRepository {

    fun getMessage(): Maybe<Message>
}