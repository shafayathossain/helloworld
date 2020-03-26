package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.main.model.Message
import com.example.helloworld.core.data.network.onResponse
import io.reactivex.Maybe
import javax.inject.Inject

class MessageNetworkSourceImpl @Inject constructor(private val networkService: MessageNetworkService): MessageNetworkSource {

    override fun getMessage(): Maybe<Message> {
        return networkService.getMessage()
            .onResponse()
    }
}