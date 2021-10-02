package com.example.helloworld.data.main

import com.example.helloworld.core.data.localdb.AppDatabase
import com.example.helloworld.core.data.network.RetrofitException
import com.example.helloworld.data.datasources.networksource.MessageNetworkSource
import com.example.helloworld.core.data.preference.AppPreference
import com.example.helloworld.data.main.model.Message
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val networkSource: MessageNetworkSource,
                                             private val db: AppDatabase,
                                             private val preference: AppPreference) :
    MainRepository {

    companion object {
        const val DEFAULT_MESSAGE = "Hello World!"
    }

    /**
     *   fetch message from network
     *       if no internet connection error occured, than
     *           fetch message from local db
     *           if any error occurs while fetching data from local db, than
     *               get message from preference
     *   on message retrieved successfully save message into preference and local db
     */

    override fun getMessage(): Maybe<Message> {
        return networkSource.getMessage()
            .onErrorResumeNext { throwable: Throwable ->
                if(throwable is RetrofitException) {
                    if(throwable.getKind() == RetrofitException.Kind.NETWORK) {
                        getMessageFromDb()
                    } else Maybe.error(throwable)
                } else Maybe.error(throwable)
            }.map {
                saveAndReturnMessage(it)
            }
    }

    private fun getMessageFromDb(): Maybe<Message> {
        return db.messageDao().getMessage()
            .onErrorResumeNext { _ ->
                getMessageFromPreference()
            }
            .flatMapMaybe {
                Maybe.create<Message>{ emitter ->
                    emitter.onSuccess(it) }
            }
    }

    private fun getMessageFromPreference(): Single<Message> {
        return Single.create<Message> {
            it.onSuccess(Message(message = preference.message))
        }
    }

    private fun saveAndReturnMessage(pMessage: Message): Message {
        var message = pMessage
        if(pMessage.message.isEmpty()) {
            message = Message(message = DEFAULT_MESSAGE)
        }
        preference.message = message.message
        db.messageDao().insertMessage(message)
        return message
    }
}