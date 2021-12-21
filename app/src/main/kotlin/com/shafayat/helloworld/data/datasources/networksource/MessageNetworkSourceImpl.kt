package com.shafayat.helloworld.data.datasources.networksource

import com.shafayat.helloworld.data.main.model.Message
import com.shafayat.helloworld.core.data.network.onResponse
import io.reactivex.Maybe
import javax.inject.Inject

class MessageNetworkSourceImpl @Inject constructor(private val networkService: MessageNetworkService): MessageNetworkSource {

    override fun getMessage(): Maybe<Message> {
        return networkService.getMessage()
            .onResponse()
    }
}