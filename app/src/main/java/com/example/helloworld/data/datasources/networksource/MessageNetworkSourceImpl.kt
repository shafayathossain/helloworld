package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.main.model.Message
import com.example.helloworld.core.data.network.onResponse
import com.example.helloworld.core.data.network.responseOrException
import io.reactivex.Maybe
import retrofit2.Response
import javax.inject.Inject

class MessageNetworkSourceImpl @Inject constructor(private val networkService: MessageNetworkService): MessageNetworkSource {

    override suspend fun getCoroutineMessage() : Response<Message> {
        return networkService.getCoroutineMessage()
            .responseOrException()
    }
}