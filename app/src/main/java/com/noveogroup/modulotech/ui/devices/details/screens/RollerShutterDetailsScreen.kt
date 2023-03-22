package com.noveogroup.modulotech.ui.devices.details.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.ui.common.DrawableImage
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(regularPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrawableImage(
            image = R.drawable.ic_roller,
            modifier = Modifier.size(deviceIcon),
            tint = Color.Gray
        )
        Text(
            text = stringResource(
                R.string.DeviceDetails_roller_shutter_position,
                roller.value.toInt()
            ),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(halfPadding))
        Slider(
            value = roller.rawValue,
            onValueChange = { rollerDetailsChanged(roller.copy(rawValue = it)) },
            valueRange = roller.valueRange,
            modifier = Modifier.fillMaxWidth()
        )
    }
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