package com.noveogroup.modulotech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.noveogroup.modulotech.ui.devices.list.DevicesListScreen
import com.noveogroup.modulotech.ui.profile.UserProfileScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Routes.DEVICES_LIST,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.DEVICES_LIST) {
            DevicesListScreen(modifier = modifier) {

            }
        }
        composable(Routes.USER_PROFILE) { UserProfileScreen(modifier) }
    }
}