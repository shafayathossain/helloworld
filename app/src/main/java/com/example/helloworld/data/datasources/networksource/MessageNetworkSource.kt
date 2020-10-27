package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.main.model.Message
import retrofit2.Response

interface MessageNetworkSource {
    suspend fun getMessage() : Response<Message>
}