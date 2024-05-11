package com.virendra.bluetoothapp.data.localdata

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Message::class], version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun messageDao(): MessageDao

}