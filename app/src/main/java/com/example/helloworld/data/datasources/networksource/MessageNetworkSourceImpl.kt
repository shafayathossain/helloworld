package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.model.Message
import com.example.helloworld.core.network.onResponse
import io.reactivex.Flowable
import io.reactivex.Maybe
import javax.inject.Inject

class MessageNetworkSourceImpl @Inject constructor(private val networkService: MessageNetworkService): MessageNetworkSource {

    override fun getMessage(): Maybe<Message> {
        return networkService.getMessage()
            .onResponse()
    }
}