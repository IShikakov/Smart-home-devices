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
import com.noveogroup.modulotech.ui.devices.details.model.LightDetailsPreview
import com.noveogroup.modulotech.ui.theme.deviceIcon
import com.noveogroup.modulotech.ui.theme.halfPadding
import com.noveogroup.modulotech.ui.theme.regularPadding

@Composable
fun LightDetailsScreen(
    light: LightDetailsPreview,
    lightDetailsChanged: (LightDetailsPreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitDetailsScreen(
            light = light,
            lightDetailsChanged = lightDetailsChanged,
            modifier = modifier
        )
    } else {
        LandscapeDetailsScreen(
            light = light,
            lightDetailsChanged = lightDetailsChanged,
            modifier = modifier
        )
    }
}

@Composable
private fun PortraitDetailsScreen(
    light: LightDetailsPreview,
    lightDetailsChanged: (LightDetailsPreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = light.color
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LightIcon(tint = color)
        DeviceModeRow(
            mode = light.mode,
            toggleMode = { lightDetailsChanged(light.copy(mode = light.mode.opposite())) }
        )
        IntensityText(value = light.value.toInt())
        Spacer(modifier = Modifier.height(halfPadding))
        IntensitySlider(
            value = light.rawValue,
            range = light.valueRange,
            color = color,
            changeIntensity = { lightDetailsChanged(light.copy(rawValue = it)) }
        )
    }
}

@Composable
private fun LandscapeDetailsScreen(
    light: LightDetailsPreview,
    lightDetailsChanged: (LightDetailsPreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = light.color
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DrawableIcon(
            image = R.drawable.ic_light,
            modifier = Modifier.size(deviceIcon),
            tint = color
        )
        Spacer(modifier = Modifier.width(halfPadding))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DeviceModeRow(
                mode = light.mode,
                toggleMode = { lightDetailsChanged(light.copy(mode = light.mode.opposite())) }
            )
            IntensityText(value = light.value.toInt())
            IntensitySlider(
                value = light.rawValue,
                range = light.valueRange,
                color = color,
                changeIntensity = { lightDetailsChanged(light.copy(rawValue = it)) }
            )
        }
    }

}

@Composable
private fun LightIcon(
    tint: Color,
) {
    DrawableIcon(
        image = R.drawable.ic_light,
        modifier = Modifier.size(deviceIcon),
        tint = tint
    )
}

@Composable
private fun IntensityText(
    value: Int,
) {
    Text(
        text = stringResource(R.string.Device_details_light_intensity, value),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun IntensitySlider(
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    changeIntensity: (Float) -> Unit,
    color: Color,
) {
    Slider(
        value = value,
        onValueChange = changeIntensity,
        valueRange = range,
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor = color
        )
    )
}

private val LightDetailsPreview.color: Color
    get() = when (mode) {
        DeviceMode.Off -> Color.Gray
        DeviceMode.On -> Color(
            ColorUtils.blendARGB(
                Color.Gray.toArgb(),
                Color.Yellow.toArgb(),
                rawValue / valueRange.endInclusive
            )
        )
    }

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewPortraitLightDetailsScreen() {
    var detailsPreview by remember { mutableStateOf(detailsPreview) }
    PortraitDetailsScreen(
        light = detailsPreview,
        lightDetailsChanged = { detailsPreview = it },
    )
}

@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, locale = "en")
@Composable
private fun PreviewLandscapeLightDetailsScreen() {
    var detailsPreview by remember { mutableStateOf(detailsPreview) }
    LandscapeDetailsScreen(
        light = detailsPreview,
        lightDetailsChanged = { detailsPreview = it },
    )
}


private val detailsPreview = LightDetailsPreview(
    id = "0",
    name = "Light",
    rawValue = 10f,
    valueRange = 0f..100f,
    valueStep = 1f,
    mode = DeviceMode.On
)
