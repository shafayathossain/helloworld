package com.example.helloworld.data.datasources.networksource

import com.example.helloworld.data.model.Message
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface MessageNetworkService {

    @GET("resources/helloworld.json")
    fun getMessage(): Maybe<Response<Message>>
}