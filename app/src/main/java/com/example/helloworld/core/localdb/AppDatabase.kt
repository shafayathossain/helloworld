package com.example.helloworld.core.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.helloworld.data.datasources.localdb.MessageDao
import com.example.helloworld.data.model.Message

@Database(entities = arrayOf(
    Message::class
), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun messageDao(): MessageDao
}