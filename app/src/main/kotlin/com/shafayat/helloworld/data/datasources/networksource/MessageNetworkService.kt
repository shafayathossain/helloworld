package com.shafayat.helloworld.data.datasources.networksource

import com.shafayat.helloworld.data.main.model.Message
import io.reactivex.Maybe
import retrofit2.Response
import retrofit2.http.GET

interface MessageNetworkService {

    @GET("resources/helloworld.json")
    fun getMessage(): Maybe<Response<Message>>
}