package com.example.helloworld.data.main

import com.example.helloworld.data.main.model.Message
import io.reactivex.Maybe

interface MainRepository {
    suspend fun getCoroutineMessage(): Message
}