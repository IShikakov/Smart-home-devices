package com.noveogroup.modulotech.ui.devices.details.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.ui.common.views.DrawableIcon

@Composable
fun DeviceModeRow(
    mode: DeviceMode,
    toggleMode: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.Device_details_mode),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(1f),
        )
        IconButton(
            onClick = toggleMode
        ) {
            DrawableIcon(
                image = R.drawable.ic_power,
                tint = when (mode) {
                    DeviceMode.On -> Color.Green
                    DeviceMode.Off -> Color.Red
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewModeRow() {
    var mode by remember { mutableStateOf(DeviceMode.Off) }
    DeviceModeRow(
        mode = mode,
        toggleMode = { mode = mode.opposite() }
    )
}