package com.virendra.bluetoothapp.di

import android.content.Context
import com.virendra.bluetoothapp.data.MessageRepositoryImpl
import com.virendra.bluetoothapp.data.localdata.MessageDao
import com.virendra.bluetoothapp.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMessageRepository(
        @ApplicationContext context: Context,
         messageDao: MessageDao
    ) : MessageRepository{
        return MessageRepositoryImpl(messageDao, context)
    }

}