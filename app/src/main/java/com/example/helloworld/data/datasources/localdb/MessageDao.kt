package com.example.helloworld.data.datasources.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.helloworld.data.main.model.Message
import io.reactivex.Single

@Dao
interface MessageDao {

    @Query("SELECT * FROM Message")
    suspend fun getMessage(): Message

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: Message)
}