package com.example.helloworld.data.main.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Message",
    indices = arrayOf(Index(value = ["message"], unique = true)))
data class Message(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "message")
    @SerializedName("message")
    var message: String = ""
)