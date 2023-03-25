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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.ColorUtils
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.ui.common.DrawableImage
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
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitDetailsScreen(
            heater = heater,
            heaterDetailsChanged = heaterDetailsChanged,
            color = color,
            modifier = modifier
        )
    } else {
        LandscapeDetailsScreen(
            heater = heater,
            heaterDetailsChanged = heaterDetailsChanged,
            color = color,
            modifier = modifier
        )
    }
}

@Composable
private fun PortraitDetailsScreen(
    heater: HeaterDetailsPreview,
    heaterDetailsChanged: (HeaterDetailsPreview) -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaterIcon(color)
        ModeRow(
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
    color: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaterIcon(color)
        Spacer(modifier = Modifier.width(halfPadding))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ModeRow(
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
    DrawableImage(
        image = R.drawable.ic_heater,
        modifier = Modifier.size(deviceIcon),
        tint = tint
    )
}

@Composable
private fun ModeRow(
    mode: DeviceMode,
    toggleMode: () -> Unit,
) {
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
            onClick = toggleMode
        ) {
            DrawableImage(
                image = R.drawable.ic_power,
                tint = when (mode) {
                    DeviceMode.ON -> Color.Green
                    DeviceMode.OFF -> Color.Red
                }
            )
        }
    }
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

@Preview
@Composable
private fun PreviewHeaterDetailsScreen() {
    var heater by remember {
        mutableStateOf(
            HeaterDetailsPreview(
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