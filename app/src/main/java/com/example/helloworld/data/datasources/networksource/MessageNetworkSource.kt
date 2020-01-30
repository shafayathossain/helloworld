package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.model.Message
import com.example.helloworld.core.network.onResponse
import io.reactivex.Flowable
import javax.inject.Inject

class MessageNetworkSource @Inject constructor(private val networkService: MessageNetworkService) {

    fun getMessage(): Flowable<Message> {
        return networkService.getMessage()
            .onResponse()
    }
}