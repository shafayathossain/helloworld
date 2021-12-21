package com.shafayat.helloworld.core.data.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shafayat.helloworld.data.datasources.localdb.MessageDao
import com.shafayat.helloworld.data.main.model.Message

@Database(
    entities = arrayOf(
        Message::class
    ), version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
}