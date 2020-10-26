package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.main.model.Message
import io.reactivex.Maybe
import retrofit2.Response

interface MessageNetworkSource {

    fun getMessage(): Maybe<Message>
    suspend fun getCoroutineMessage() : Response<Message>
}