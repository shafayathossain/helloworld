package com.example.helloworld.data.repository.main

import com.example.helloworld.data.datasources.networksource.MessageNetworkSource
import com.example.helloworld.data.model.Message
import com.example.helloworld.data.repository.main.MainRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val networkSource: MessageNetworkSource) :
    MainRepository {

    override fun getMessage(): Flowable<Message> {
        return networkSource.getMessage()
    }
}