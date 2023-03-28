package com.noveogroup.modulotech.ui.common.views

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noveogroup.modulotech.R

@Composable
fun DrawableIcon(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    @StringRes contentDescription: Int? = null,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
) {
    Icon(
        painter = painterResource(image),
        contentDescription = contentDescription?.let { stringResource(it) },
        modifier = modifier,
        tint = tint,
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewDrawableIcon() {
    DrawableIcon(
        image = R.drawable.ic_moon,
        modifier = Modifier.size(48.dp),
        tint = Color.Blue,
    )
}
