package com.example.helloworld.data.main

import com.example.helloworld.data.main.model.Message

interface MainRepository {
    suspend fun getMessage(): Message
}