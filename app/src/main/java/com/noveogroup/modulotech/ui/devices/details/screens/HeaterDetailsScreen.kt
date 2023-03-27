package com.noveogroup.modulotech.ui.devices.details.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.ColorUtils
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.ui.common.views.DrawableIcon
import com.noveogroup.modulotech.ui.devices.details.common.DeviceModeRow
import com.noveogroup.modulotech.ui.devices.details.model.HeaterDetailsPreview
import com.noveogroup.modulotech.ui.theme.deviceIcon
import com.noveogroup.modulotech.ui.theme.halfPadding
import com.noveogroup.modulotech.ui.theme.regularPadding

@Composable
fun HeaterDetailsScreen(
    heater: HeaterDetailsPreview,
    heaterDetailsChanged: (HeaterDetailsPreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitDetailsScreen(
            heater = heater,
            heaterDetailsChanged = heaterDetailsChanged,
            modifier = modifier
        )
    } else {
        LandscapeDetailsScreen(
            heater = heater,
            heaterDetailsChanged = heaterDetailsChanged,
            modifier = modifier
        )
    }
}

@Composable
private fun PortraitDetailsScreen(
    heater: HeaterDetailsPreview,
    heaterDetailsChanged: (HeaterDetailsPreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = heater.color
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaterIcon(color)
        DeviceModeRow(
            mode = heater.mode,
            toggleMode = { heaterDetailsChanged(heater.copy(mode = heater.mode.opposite())) }
        )
        TemperatureText(heater.value)
        Spacer(modifier = Modifier.height(halfPadding))
        TemperatureSlider(
            value = heater.rawValue,
            range = heater.valueRange,
            changeTemperature = { heaterDetailsChanged(heater.copy(rawValue = it)) },
            color = color
        )
    }
}

@Composable
private fun LandscapeDetailsScreen(
    heater: HeaterDetailsPreview,
    heaterDetailsChanged: (HeaterDetailsPreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = heater.color
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaterIcon(color)
        Spacer(modifier = Modifier.width(halfPadding))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DeviceModeRow(
                mode = heater.mode,
                toggleMode = { heaterDetailsChanged(heater.copy(mode = heater.mode.opposite())) }
            )
            TemperatureText(heater.value)
            TemperatureSlider(
                value = heater.rawValue,
                range = heater.valueRange,
                changeTemperature = { heaterDetailsChanged(heater.copy(rawValue = it)) },
                color = color
            )
        }
    }
}

@Composable
private fun HeaterIcon(
    tint: Color,
) {
    DrawableIcon(
        image = R.drawable.ic_heater,
        modifier = Modifier.size(deviceIcon),
        tint = tint
    )
}

@Composable
private fun TemperatureText(
    value: Float,
) {
    Text(
        text = stringResource(R.string.DeviceDetails_heater_temperature, value),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun TemperatureSlider(
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    changeTemperature: (Float) -> Unit,
    color: Color,
) {
    Slider(
        value = value,
        onValueChange = changeTemperature,
        valueRange = range,
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor = color
        )
    )
}

private val HeaterDetailsPreview.color: Color
    get() = when (mode) {
        DeviceMode.OFF -> Color.Blue
        DeviceMode.ON -> Color(
            ColorUtils.blendARGB(
                Color.Blue.toArgb(),
                Color.Red.toArgb(),
                rawValue / valueRange.endInclusive
            )
        )
    }

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewPortraitHeaterDetailsScreen() {
    var detailsPreview by remember { mutableStateOf(detailsPreview) }
    PortraitDetailsScreen(
        heater = detailsPreview,
        heaterDetailsChanged = { detailsPreview = it },
    )
}

@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, locale = "en")
@Composable
private fun PreviewLandscapeHeaterDetailsScreen() {
    var detailsPreview by remember { mutableStateOf(detailsPreview) }
    LandscapeDetailsScreen(
        heater = detailsPreview,
        heaterDetailsChanged = { detailsPreview = it },
    )
}

private val detailsPreview = HeaterDetailsPreview(
    id = "0",
    name = "Heater",
    rawValue = 7.0f,
    valueRange = 7f..28f,
    valueStep = 0.5f,
    mode = DeviceMode.ON
)