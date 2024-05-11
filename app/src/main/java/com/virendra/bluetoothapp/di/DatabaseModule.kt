package com.virendra.bluetoothapp.di

import android.content.Context
import androidx.room.Room
import com.virendra.bluetoothapp.data.localdata.DataBase
import com.virendra.bluetoothapp.data.localdata.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideMessageDao(database: DataBase): MessageDao {
        return database.messageDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): DataBase {
        return Room.databaseBuilder(app, DataBase::class.java, "message_database")
            .build()
    }

}