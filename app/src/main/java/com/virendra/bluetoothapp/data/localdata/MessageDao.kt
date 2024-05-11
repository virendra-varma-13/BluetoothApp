package com.virendra.bluetoothapp.data.localdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): List<Message>

    @Query("SELECT * FROM message WHERE messageId = :id")
    fun getById(id: Int): Message

    @Insert
    fun insert(message: Message)

}