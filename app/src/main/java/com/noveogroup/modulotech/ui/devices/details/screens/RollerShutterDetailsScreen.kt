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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.ui.common.DrawableImage
import com.noveogroup.modulotech.ui.common.verticalPosition
import com.noveogroup.modulotech.ui.devices.details.model.DeviceDetailsPreview
import com.noveogroup.modulotech.ui.theme.deviceIcon
import com.noveogroup.modulotech.ui.theme.halfPadding
import com.noveogroup.modulotech.ui.theme.regularPadding

@Composable
fun RollerShutterDetailsScreen(
    roller: DeviceDetailsPreview.RollerShutter,
    rollerDetailsChanged: (DeviceDetailsPreview.RollerShutter) -> Unit,
    modifier: Modifier = Modifier,
) {
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitDetailsScreen(
            roller = roller,
            rollerDetailsChanged = rollerDetailsChanged,
            modifier = modifier
        )
    } else {
        LandscapeDetailsScreen(
            roller = roller,
            rollerDetailsChanged = rollerDetailsChanged,
            modifier = modifier
        )
    }
}

@Composable
private fun PortraitDetailsScreen(
    roller: DeviceDetailsPreview.RollerShutter,
    rollerDetailsChanged: (DeviceDetailsPreview.RollerShutter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RollerIcon()
        PositionText(value = roller.value.toInt())
        Spacer(modifier = Modifier.height(halfPadding))
        PositionSlider(
            value = roller.rawValue,
            range = roller.valueRange,
            changePosition = { rollerDetailsChanged(roller.copy(rawValue = it)) },
        )
    }
}

@Composable
private fun LandscapeDetailsScreen(
    roller: DeviceDetailsPreview.RollerShutter,
    rollerDetailsChanged: (DeviceDetailsPreview.RollerShutter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RollerIcon()
        Spacer(modifier = Modifier.width(halfPadding))
        PositionSlider(
            value = roller.rawValue,
            range = roller.valueRange,
            changePosition = { rollerDetailsChanged(roller.copy(rawValue = it)) },
        )
        Spacer(modifier = Modifier.width(halfPadding))
        PositionText(value = roller.value.toInt())
    }
}

@Composable
private fun RollerIcon(
    tint: Color = Color.Gray,
) {
    DrawableImage(
        image = R.drawable.ic_roller,
        modifier = Modifier.size(deviceIcon),
        tint = tint
    )
}

@Composable
private fun PositionText(
    value: Int,
) {
    Text(
        text = stringResource(R.string.DeviceDetails_roller_shutter_position, value),
        style = MaterialTheme.typography.subtitle1,
    )
}

@Composable
private fun PositionSlider(
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    changePosition: (Float) -> Unit,
) {
    Slider(
        value = value,
        valueRange = range,
        onValueChange = changePosition,
        modifier = Modifier
            .verticalPosition()
            .fillMaxWidth()
    )
}

@Preview
@Composable
private fun PreviewRollerShutterDetailsScreen() {
    var roller by remember {
        mutableStateOf(
            DeviceDetailsPreview.RollerShutter(
                id = "0",
                name = "Roller Shutter",
                rawValue = 45f,
                valueRange = 0f..100f,
                valueStep = 1f
            )
        )
    }
    RollerShutterDetailsScreen(
        roller = roller,
        rollerDetailsChanged = { roller = it }
    )
}