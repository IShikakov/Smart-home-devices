package com.noveogroup.modulotech.ui.devices.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.ui.devices.details.model.HeaterDetailsPreview
import com.noveogroup.modulotech.ui.devices.details.model.LightDetailsPreview
import com.noveogroup.modulotech.ui.devices.details.model.RollerShutterDetailsPreview
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
    back: () -> Unit,
) {
    val deviceDetails by viewModel.deviceDetails.collectAsState()
    deviceDetails?.let { details ->
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = { DeviceDetailsTopAppBar(details.name, back) },
            content = { paddingValues ->
                when (details) {
                    is LightDetailsPreview -> LightDetailsScreen(
                        light = details,
                        modifier = modifier.padding(paddingValues),
                        lightDetailsChanged = viewModel::updateDeviceDetails
                    )
                    is HeaterDetailsPreview -> HeaterDetailsScreen(
                        heater = details,
                        modifier = modifier.padding(paddingValues),
                        heaterDetailsChanged = viewModel::updateDeviceDetails
                    )
                    is RollerShutterDetailsPreview -> RollerShutterDetailsScreen(
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
private fun DeviceDetailsTopAppBar(
    deviceName: String,
    back: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = deviceName) },
        navigationIcon = {
            IconButton(onClick = back) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_icon_description)
                )
            }
        }
    )
}