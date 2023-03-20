package com.noveogroup.modulotech.ui.devices.list

import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.DevicesListInteractor
import com.noveogroup.modulotech.domain.synchronization.DataSyncInteractor
import com.noveogroup.modulotech.ui.base.BaseViewModel
import com.noveogroup.modulotech.ui.common.ResourcesManager
import com.noveogroup.modulotech.ui.devices.list.common.DevicesListMapper
import com.noveogroup.modulotech.ui.devices.list.model.DevicePreview
import com.noveogroup.modulotech.ui.devices.list.model.DevicesFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DevicesListViewModel(
    private val devicesInteractor: DevicesListInteractor,
    private val dataSyncInteractor: DataSyncInteractor,
    private val devicesListMapper: DevicesListMapper,
    private val resourcesManager: ResourcesManager,
) : BaseViewModel() {

    private val _devices: MutableStateFlow<List<DevicePreview>> = MutableStateFlow(emptyList())
    val devices: StateFlow<List<DevicePreview>> = _devices

    private val _filters: MutableStateFlow<List<DevicesFilter>> = MutableStateFlow(emptyList())
    val filters: StateFlow<List<DevicesFilter>> = _filters

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        viewModelScope.launch {
            syncDataIfRequired()
            observeDevices()
        }
        observeFilters()
    }

    private suspend fun syncDataIfRequired() {
        try {
            if (dataSyncInteractor.isSyncRequired()) {
                _loading.value = true
                dataSyncInteractor.syncData()
            }
        } catch (error: Exception) {
            showErrorMessage()
        } finally {
            _loading.value = false
        }
    }

    private fun observeDevices() {
        devicesInteractor.observeDevices()
            .map { devices -> devicesListMapper.mapDevice(devices) }
            .onEach { previews -> _devices.value = previews }
            .catch { showErrorMessage() }
            .launchIn(viewModelScope)
    }

    private fun observeFilters() {
        devicesInteractor.observeFilters()
            .map { filtersState -> devicesListMapper.mapFilter(filtersState) }
            .onEach { filters -> _filters.value = filters }
            .catch { showErrorMessage() }
            .launchIn(viewModelScope)
    }

    fun deleteDevice(device: DevicePreview) {
        viewModelScope.launch {
            try {
                devicesInteractor.deleteDevice(device.id)
            } catch (error: Exception) {
                showErrorMessage()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                _loading.value = true
                dataSyncInteractor.syncData()
            } catch (error: Exception) {
                showErrorMessage()
            } finally {
                _loading.value = false
            }
        }
    }

    fun filterClicked(filter: DevicesFilter) {
        viewModelScope.launch {
            devicesInteractor.toggleDevicesFilter(filter.type)
        }
    }

    private fun showErrorMessage() {
        showMessage(resourcesManager.resolveString(R.string.error_message))
    }

}