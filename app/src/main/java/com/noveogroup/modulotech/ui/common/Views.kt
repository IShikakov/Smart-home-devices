package com.noveogroup.modulotech.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun DrawableImage(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    @StringRes contentDescription: Int? = null,
) {
    Icon(
        painter = painterResource(image),
        contentDescription = contentDescription?.let { stringResource(it) },
        modifier = modifier,
    )

}