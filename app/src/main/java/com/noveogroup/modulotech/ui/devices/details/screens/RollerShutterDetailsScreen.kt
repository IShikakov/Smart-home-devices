package com.noveogroup.modulotech.ui.devices.details.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.ui.common.views.DrawableIcon
import com.noveogroup.modulotech.ui.common.views.VerticalSlider
import com.noveogroup.modulotech.ui.devices.details.model.RollerShutterDetailsPreview
import com.noveogroup.modulotech.ui.theme.deviceIcon
import com.noveogroup.modulotech.ui.theme.halfPadding
import com.noveogroup.modulotech.ui.theme.regularPadding

@Composable
fun RollerShutterDetailsScreen(
    roller: RollerShutterDetailsPreview,
    rollerDetailsChanged: (RollerShutterDetailsPreview) -> Unit,
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
    roller: RollerShutterDetailsPreview,
    rollerDetailsChanged: (RollerShutterDetailsPreview) -> Unit,
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
        Spacer(modifier = Modifier.height(regularPadding))
        PositionSlider(
            value = roller.rawValue,
            range = roller.valueRange,
            changePosition = { rollerDetailsChanged(roller.copy(rawValue = it)) },
            modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
private fun LandscapeDetailsScreen(
    roller: RollerShutterDetailsPreview,
    rollerDetailsChanged: (RollerShutterDetailsPreview) -> Unit,
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
            modifier = modifier.height(deviceIcon)
        )
        Spacer(modifier = Modifier.width(halfPadding))
        PositionText(value = roller.value.toInt())
    }
}

@Composable
private fun RollerIcon(
    tint: Color = Color.Gray,
) {
    DrawableIcon(
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
        text = stringResource(R.string.Device_details_roller_shutter_position, value),
        style = MaterialTheme.typography.subtitle1,
    )
}

@Composable
private fun PositionSlider(
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    changePosition: (Float) -> Unit,
    modifier: Modifier,
) {
    VerticalSlider(
        value = value,
        valueRange = range,
        onValueChange = changePosition,
        modifier = modifier
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewPortraitRollerDetailsScreen() {
    var detailsPreview by remember { mutableStateOf(detailsPreview) }
    PortraitDetailsScreen(
        roller = detailsPreview,
        rollerDetailsChanged = { detailsPreview = it }
    )
}

@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, locale = "en")
@Composable
private fun PreviewLandscapeRollerDetailsScreen() {
    var detailsPreview by remember { mutableStateOf(detailsPreview) }
    LandscapeDetailsScreen(
        roller = detailsPreview,
        rollerDetailsChanged = { detailsPreview = it }
    )
}

private val detailsPreview = RollerShutterDetailsPreview(
    id = "0",
    name = "Roller Shutter",
    rawValue = 45f,
    valueRange = 0f..100f,
    valueStep = 1f
)
