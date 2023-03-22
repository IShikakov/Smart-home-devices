package com.noveogroup.modulotech.ui.devices.details.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.ColorUtils
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.ui.common.DrawableImage
import com.noveogroup.modulotech.ui.devices.details.model.DeviceDetailsPreview
import com.noveogroup.modulotech.ui.theme.deviceIcon
import com.noveogroup.modulotech.ui.theme.halfPadding
import com.noveogroup.modulotech.ui.theme.regularPadding

@Composable
fun HeaterDetailsScreen(
    heater: DeviceDetailsPreview.Heater,
    heaterDetailsChanged: (DeviceDetailsPreview.Heater) -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = when (heater.mode) {
        DeviceMode.OFF -> Color.Blue
        DeviceMode.ON -> Color(
            ColorUtils.blendARGB(
                Color.Blue.toArgb(),
                Color.Red.toArgb(),
                heater.rawValue / heater.valueRange.endInclusive
            )
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrawableImage(
            image = R.drawable.ic_heater,
            modifier = Modifier.size(deviceIcon),
            tint = color
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.DeviceDetails_mode),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.weight(1f),
            )
            IconButton(
                onClick = { heaterDetailsChanged(heater.copy(mode = heater.mode.opposite())) }
            ) {
                DrawableImage(
                    image = R.drawable.ic_power,
                    tint = when (heater.mode) {
                        DeviceMode.ON -> Color.Green
                        DeviceMode.OFF -> Color.Red
                    }
                )
            }
        }
        Text(
            text = stringResource(R.string.DeviceDetails_heater_temperature, heater.value),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(halfPadding))
        Slider(
            value = heater.rawValue,
            onValueChange = { heaterDetailsChanged(heater.copy(rawValue = it)) },
            valueRange = heater.valueRange,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = color,
                activeTrackColor = color
            )
        )
    }
}

@Preview
@Composable
private fun PreviewHeaterDetailsScreen() {
    var heater by remember {
        mutableStateOf(
            DeviceDetailsPreview.Heater(
                id = "0",
                name = "Heater",
                rawValue = 7.0f,
                valueRange = 7f..28f,
                valueStep = 0.5f,
                mode = DeviceMode.ON
            )
        )
    }
    HeaterDetailsScreen(
        heater = heater,
        heaterDetailsChanged = { heater = it }
    )
}