package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.model.Message
import com.example.helloworld.utils.onResponse
import io.reactivex.Single
import javax.inject.Inject

class MessageNetworkSource @Inject constructor(private val networkService: MessageNetworkService) {

    fun getMessage(): Single<Message> {
        return networkService.getMessage()
            .onResponse()
    }
}