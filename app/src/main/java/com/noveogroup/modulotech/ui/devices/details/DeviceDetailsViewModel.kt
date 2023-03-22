package com.noveogroup.modulotech.ui.devices.details

import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.details.DeviceDetailsInteractor
import com.noveogroup.modulotech.ui.base.BaseViewModel
import com.noveogroup.modulotech.ui.common.ResourcesManager
import com.noveogroup.modulotech.ui.devices.details.common.DeviceDetailsMapper
import com.noveogroup.modulotech.ui.devices.details.model.DeviceDetailsPreview
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class DeviceDetailsViewModel(
    private val deviceId: String,
    private val deviceDetailsInteractor: DeviceDetailsInteractor,
    private val mapper: DeviceDetailsMapper,
    private val resourcesManager: ResourcesManager,
) : BaseViewModel() {

    private val _deviceDetails: MutableStateFlow<DeviceDetailsPreview?> = MutableStateFlow(null)
    val deviceDetails: StateFlow<DeviceDetailsPreview?> = _deviceDetails

    init {
        observeDeviceUpdates()
        viewModelScope.launch {
            val device = deviceDetailsInteractor.getDeviceById(deviceId)
            _deviceDetails.value = mapper.mapToPreview(device)
        }
    }

    private fun observeDeviceUpdates() {
        deviceDetails
            .filterNotNull()
            .drop(NUMBER_OF_INITIAL_VALUES)
            .distinctUntilChanged()
            .debounce(DEVICE_UPDATE_DEBOUNCE)
            .onEach { details ->
                val device = mapper.mapToDevice(details)
                deviceDetailsInteractor.updateDevice(device)
            }
            .catch { showErrorMessage() }
            .launchIn(viewModelScope)
    }

    fun updateDeviceDetails(deviceDetails: DeviceDetailsPreview) {
        _deviceDetails.value = deviceDetails
    }

    private fun showErrorMessage() {
        showMessage(resourcesManager.resolveString(R.string.error_message))
    }

    companion object {
        private const val DEVICE_UPDATE_DEBOUNCE = 1000L
        private const val NUMBER_OF_INITIAL_VALUES = 1
    }

}