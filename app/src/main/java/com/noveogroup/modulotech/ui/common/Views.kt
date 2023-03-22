package com.noveogroup.modulotech.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun DrawableImage(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    @StringRes contentDescription: Int? = null,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
) {
    Icon(
        painter = painterResource(image),
        contentDescription = contentDescription?.let { stringResource(it) },
        modifier = modifier,
        tint = tint
    )
}