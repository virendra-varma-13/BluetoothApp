package com.virendra.bluetoothapp.domain.repository

import android.bluetooth.BluetoothDevice
import com.virendra.bluetoothapp.data.localdata.Message
import com.virendra.bluetoothapp.domain.model.BluetoothDeviceDomain
import kotlinx.coroutines.flow.StateFlow

interface MessageRepository {

    val scannedDevices: StateFlow<List<BluetoothDeviceDomain>>
    val pairedDevices: StateFlow<List<BluetoothDeviceDomain>>

    fun startScan()
    fun stopScan()

    fun connectToDevice(device: BluetoothDevice)
    fun disconnectFromDevice(device: BluetoothDevice)

    fun release()
    suspend fun save(message: Message): Unit

    suspend fun get(): List<Message>

}