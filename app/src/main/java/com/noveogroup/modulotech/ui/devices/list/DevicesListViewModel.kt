package com.noveogroup.modulotech.ui.devices.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.DevicesInteractor
import com.noveogroup.modulotech.ui.common.ResourcesManager
import com.noveogroup.modulotech.ui.devices.list.common.DevicesListMapper
import com.noveogroup.modulotech.ui.devices.list.model.DevicePreview
import com.noveogroup.modulotech.ui.devices.list.model.DevicesFilter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DevicesListViewModel(
    private val devicesInteractor: DevicesInteractor,
    private val devicesListMapper: DevicesListMapper,
    private val resourcesManager: ResourcesManager,
) : ViewModel() {

    private val _devices: MutableStateFlow<List<DevicePreview>> = MutableStateFlow(emptyList())
    val devices: StateFlow<List<DevicePreview>> = _devices

    private val _filters: MutableStateFlow<List<DevicesFilter>> = MutableStateFlow(emptyList())
    val filters: StateFlow<List<DevicesFilter>> = _filters

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        observeDevices()
        observeFilters()
    }

    private fun observeDevices() {
        _loading.value = true
        devicesInteractor.observeDevices()
            .map { devices -> devicesListMapper.mapDevice(devices) }
            .onEach { previews ->
                _loading.value = false
                _devices.value = previews
            }
            .catch { error ->
                _loading.value = false
                handleError(error)
            }
            .launchIn(viewModelScope)
    }

    private fun observeFilters() {
        devicesInteractor.observeFilters()
            .map { filtersState -> devicesListMapper.mapFilter(filtersState) }
            .onEach { filters -> _filters.value = filters }
            .launchIn(viewModelScope)
    }

    fun deleteDevice(device: DevicePreview) {
        viewModelScope.launch {
            try {
                devicesInteractor.deleteDevice(device.id)
            } catch (error: Exception) {
                handleError(error)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                _loading.value = true
                devicesInteractor.refreshDevices()
            } catch (error: Exception) {
                handleError(error)
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

    /**
     * The logic can be different. At the moment it shows a snackbar for 5 seconds and then hides it.
     */
    private fun handleError(error: Throwable) {
        viewModelScope.launch {
            val message =
                resourcesManager.resolveString(R.string.error_message, error.javaClass.simpleName)
            _errorMessage.value = message
            delay(ERROR_MESSAGE_TIME)
            _errorMessage.value = null
        }
    }

    companion object {
        private const val ERROR_MESSAGE_TIME = 2000L
    }
}