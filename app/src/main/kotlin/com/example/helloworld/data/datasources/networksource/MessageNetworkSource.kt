package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.main.model.Message
import io.reactivex.Maybe

interface MessageNetworkSource {

    fun getMessage(): Maybe<Message>
}