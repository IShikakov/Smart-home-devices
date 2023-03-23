package com.noveogroup.modulotech.ui.devices.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.noveogroup.modulotech.ui.devices.details.model.DeviceDetailsPreview
import com.noveogroup.modulotech.ui.devices.details.screens.HeaterDetailsScreen
import com.noveogroup.modulotech.ui.devices.details.screens.LightDetailsScreen
import com.noveogroup.modulotech.ui.devices.details.screens.RollerShutterDetailsScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DeviceDetailsScreen(
    deviceId: String,
    modifier: Modifier = Modifier,
    viewModel: DeviceDetailsViewModel = koinViewModel(parameters = { parametersOf(deviceId) }),
) {
    val deviceDetails by viewModel.deviceDetails.collectAsState()
    deviceDetails?.let { details ->
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = { DeviceDetailsTopAppBar(details.name) },
            content = { paddingValues ->
                when (details) {
                    is DeviceDetailsPreview.Light -> LightDetailsScreen(
                        light = details,
                        modifier = modifier.padding(paddingValues),
                        lightDetailsChanged = viewModel::updateDeviceDetails
                    )
                    is DeviceDetailsPreview.Heater -> HeaterDetailsScreen(
                        heater = details,
                        modifier = modifier.padding(paddingValues),
                        heaterDetailsChanged = viewModel::updateDeviceDetails
                    )
                    is DeviceDetailsPreview.RollerShutter -> RollerShutterDetailsScreen(
                        roller = details,
                        modifier = modifier.padding(paddingValues),
                        rollerDetailsChanged = viewModel::updateDeviceDetails
                    )
                }
            }
        )
    }
}

@Composable
private fun DeviceDetailsTopAppBar(deviceName: String) {
    TopAppBar(
        title = { Text(text = deviceName) },
    )
}