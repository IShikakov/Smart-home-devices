package com.noveogroup.modulotech.ui.common

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun Snackbar(
    message: String,
    snackbarHostState: SnackbarHostState,
    duration: SnackbarDuration = SnackbarDuration.Indefinite,
) {
    LaunchedEffect(message, snackbarHostState) {
        snackbarHostState.showSnackbar(
            message = message,
            duration = duration
        )
    }
}