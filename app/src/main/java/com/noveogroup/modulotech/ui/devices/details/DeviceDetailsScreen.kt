package com.noveogroup.modulotech.ui.devices.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeviceDetailsScreen(
    deviceId: String,
    modifier: Modifier = Modifier,
) {
    Text(text = deviceId)
}