package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.main.model.Message
import com.example.helloworld.core.data.network.responseOrException
import retrofit2.Response
import javax.inject.Inject

class MessageNetworkSourceImpl @Inject constructor(private val networkService: MessageNetworkService): MessageNetworkSource {

    override suspend fun getMessage() : Response<Message> {
        return networkService.getMessage()
            .responseOrException()
    }
}