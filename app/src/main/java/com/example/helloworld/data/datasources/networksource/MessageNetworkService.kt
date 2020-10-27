package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.main.model.Message
import retrofit2.Response
import retrofit2.http.GET

interface MessageNetworkService {
    @GET("resources/helloworld.json")
    suspend fun getMessage(): Response<Message>
}