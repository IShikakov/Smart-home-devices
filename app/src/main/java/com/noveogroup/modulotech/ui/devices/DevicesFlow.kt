package com.noveogroup.modulotech.ui.devices

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noveogroup.modulotech.ui.devices.details.DeviceDetailsScreen
import com.noveogroup.modulotech.ui.devices.list.DevicesListScreen
import com.noveogroup.modulotech.ui.navigation.Screen
import com.noveogroup.modulotech.ui.navigation.getStringArg
import com.noveogroup.modulotech.ui.navigation.stringNavArg

@Composable
fun DevicesFlow(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.DevicesListScreen.route
    ) {
        composable(Screen.DevicesListScreen.route) {
            DevicesListScreen(modifier) { deviceId ->
                navController.navigate(Screen.DeviceDetailsScreen.createRoute(deviceId))
            }
        }
        composable(
            route = Screen.DeviceDetailsScreen.route,
            arguments = listOf(stringNavArg(Screen.DeviceDetailsScreen.DEVICE_ID_KEY))
        ) { backStackEntry ->
            val deviceId = backStackEntry.getStringArg(Screen.DeviceDetailsScreen.DEVICE_ID_KEY)
            DeviceDetailsScreen(deviceId, modifier)
        }
    }
}