package com.shafayat.helloworld.data.datasources.networksource

import com.shafayat.helloworld.data.main.model.Message
import io.reactivex.Maybe

interface MessageNetworkSource {

    fun getMessage(): Maybe<Message>
}