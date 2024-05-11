package com.virendra.bluetoothapp.data.mapper

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.virendra.bluetoothapp.domain.model.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}