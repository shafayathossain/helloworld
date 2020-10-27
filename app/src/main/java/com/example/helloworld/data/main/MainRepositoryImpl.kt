package com.example.helloworld.data.main

import com.example.helloworld.core.data.localdb.AppDatabase
import com.example.helloworld.core.data.network.RetrofitException
import com.example.helloworld.data.datasources.networksource.MessageNetworkSource
import com.example.helloworld.core.data.preference.AppPreference
import com.example.helloworld.data.main.model.Message
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val networkSource: MessageNetworkSource,
                                             private val db: AppDatabase,
                                             private val preference: AppPreference) :
    MainRepository {

    /**
     *   fetch message from network
     *       if no internet connection error occurred, then
     *           fetch message from local db
     *           if any error occurs while fetching data from local db, than
     *               get message from preference
     *   on message retrieved successfully save message into preference and local db
     */

    override suspend fun getMessage(): Message {

        var message = Message()

        runCatching {
            networkSource.getMessage().body()!!
        }.onSuccess {
            message = it
        }.onFailure {
            if(it is RetrofitException){
                if(it.getKind() == RetrofitException.Kind.NETWORK){
                    runCatching {
                        db.messageDao().getMessage()
                    }.onSuccess {localMessage->
                        message=localMessage
                    }.onFailure {
                        message = Message(message = preference.message)
                    }
                }else throw it
            }else throw it
        }

        if(message.message.isEmpty())
           message = Message(message = "Hello World!")

        preference.message = message.message
        db.messageDao().insertMessage(message)

        return message
    }
}