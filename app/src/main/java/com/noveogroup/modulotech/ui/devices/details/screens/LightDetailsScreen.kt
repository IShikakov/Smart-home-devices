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
fun LightDetailsScreen(
    light: DeviceDetailsPreview.Light,
    lightDetailsChanged: (DeviceDetailsPreview.Light) -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = when (light.mode) {
        DeviceMode.OFF -> Color.Gray
        DeviceMode.ON -> Color(
            ColorUtils.blendARGB(
                Color.Gray.toArgb(),
                Color.Yellow.toArgb(),
                light.rawValue / light.valueRange.endInclusive
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
            image = R.drawable.ic_light,
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
                onClick = { lightDetailsChanged(light.copy(mode = light.mode.opposite())) }
            ) {
                DrawableImage(
                    image = R.drawable.ic_power,
                    tint = when (light.mode) {
                        DeviceMode.ON -> Color.Green
                        DeviceMode.OFF -> Color.Red
                    }
                )
            }
        }
        Text(
            text = stringResource(R.string.DeviceDetails_light_intensity, light.value.toInt()),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(halfPadding))
        Slider(
            value = light.rawValue,
            onValueChange = { lightDetailsChanged(light.copy(rawValue = it)) },
            valueRange = light.valueRange,
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
private fun PreviewLightDetailsScreen() {
    var light by remember {
        mutableStateOf(
            DeviceDetailsPreview.Light(
                id = "0",
                name = "Light",
                rawValue = 10f,
                valueRange = 0f..100f,
                valueStep = 1f,
                mode = DeviceMode.ON
            )
        )
    }
    LightDetailsScreen(
        light = light,
        lightDetailsChanged = { light = it }
    )
}
