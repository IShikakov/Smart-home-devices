package com.noveogroup.modulotech.ui.common.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Snackbar(
    message: String,
    snackbarHostState: SnackbarHostState,
    duration: SnackbarDuration = SnackbarDuration.Indefinite,
) {
    LaunchedEffect(message, snackbarHostState) {
        snackbarHostState.showSnackbar(
            message = message,
            duration = duration,
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun PreviewSnackbar() {
    val state = rememberScaffoldState()
    Scaffold(scaffoldState = state) {
        Box(modifier = Modifier.padding(it)) {
            Snackbar(
                message = "Hello, I'm a Snackbar!",
                snackbarHostState = state.snackbarHostState,
            )
        }
    }
}
