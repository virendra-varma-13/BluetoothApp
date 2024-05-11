package com.virendra.bluetoothapp.presentation.dashboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virendra.bluetoothapp.domain.repository.MessageRepository
import com.virendra.bluetoothapp.presentation.dashboard.BluetoothUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    private val repository: MessageRepository) : ViewModel(){

    private val _state = MutableStateFlow(BluetoothUiState())
    val state = combine(
        repository.scannedDevices,
        repository.pairedDevices,
        _state
    ) { scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun startScan() {
        repository.startScan()
    }

    fun stopScan() {
        repository.stopScan()
    }


}