package com.virendra.bluetoothapp.data.localdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(@PrimaryKey val messageId: Int, val senderMacId: String,
                   val receiverMacId: String, val message: String, val timestamp: Long)
