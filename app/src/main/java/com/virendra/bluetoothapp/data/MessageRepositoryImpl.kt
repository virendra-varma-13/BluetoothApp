package com.virendra.bluetoothapp.data

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import com.virendra.bluetoothapp.data.localdata.Message
import com.virendra.bluetoothapp.data.localdata.MessageDao
import com.virendra.bluetoothapp.domain.model.BluetoothDeviceDomain
import com.virendra.bluetoothapp.domain.repository.MessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.virendra.bluetoothapp.data.mapper.toBluetoothDeviceDomain
import kotlinx.coroutines.flow.update

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val context: Context
) :
    MessageRepository {

    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomain>>
        get() = _scannedDevices.asStateFlow()
    override val pairedDevices: StateFlow<List<BluetoothDeviceDomain>>
        get() = _scannedDevices.asStateFlow()

   private val foundDeviceReceiver = FoundDeviceReceiver { device ->
        _scannedDevices.update { devices ->
            val newDevice = device.toBluetoothDeviceDomain()
            if(newDevice in devices) devices else devices + newDevice
        }
   }

    init {
        updatePairedDevices()
    }

    @SuppressLint("MissingPermission")
    override fun startScan() {
        if (!hasPermissions(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        context.registerReceiver(
            foundDeviceReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND)
        )

        updatePairedDevices()

        bluetoothAdapter?.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    override fun stopScan() {
        if(!hasPermissions(Manifest.permission.BLUETOOTH_SCAN)){
            return
        }
        bluetoothAdapter?.cancelDiscovery()
    }

    override fun connectToDevice(device: BluetoothDevice) {
        TODO("Not yet implemented")
    }

    override fun disconnectFromDevice(device: BluetoothDevice) {
        TODO("Not yet implemented")
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
    }

    override
    suspend fun save(message: Message): Unit {
        val result = messageDao.insert(message)
        return result
    }

    override
    suspend fun get(): List<Message> {
        val messages = messageDao.getAll()
        return messages
    }

    @SuppressLint("MissingPermission")
    private fun updatePairedDevices() {
        if (!hasPermissions(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        if (!bluetoothAdapter?.isEnabled!!) {
            return
        }
        bluetoothAdapter
            ?.bondedDevices
            ?.map { it.toBluetoothDeviceDomain() }
            ?.also { devices ->  _scannedDevices.value = devices}
    }

    private fun hasPermissions(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}